package com.weatherApp.weather;

import com.weatherApp.weather.util.DataContainer;
import com.weatherApp.weather.util.Location;
import com.weatherApp.weather.util.SupportedParametersHourly;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class DataHandler implements DataService {

    private final Map<String, Location> locationMap = new HashMap<>();
    private final Map<String, List<DataContainer>> cache = new HashMap<>();

    public DataHandler() {
        // supported locations
        this.locationMap.put("ostrava", new Location("ostrava", 49.84, 18.29));
        this.locationMap.put("roznov_pod_radhostem",
                new Location("roznov_pod_radhostem", 49.45, 18.14));
        this.locationMap.put("brno", new Location("brno", 49.19, 16.60));
        this.locationMap.put("olomouc", new Location("olomouc", 49.59, 17.25));
        this.locationMap.put("prague", new Location("prague", 50.07, 4.41));
        this.locationMap.put("dublin", new Location("dublin", 53.34, -6.26));
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    public void fetchingDataEveryHour() {
        this.cache.clear();
        this.locationMap.forEach((key, value) -> {
            Optional<String> tmp = getDataFromWeb(value.Name());
            tmp.ifPresent(s -> cache.put(key, processRawData(s)));
        });
    }

    private List<DataContainer> getOrFetch(String locationName) {
        List<DataContainer> data = new LinkedList<>();
        if (this.cache.containsKey(locationName)) {
            data = this.cache.get(locationName);
        } else {
            Optional<String> tmp = getDataFromWeb(locationName);
            if (tmp.isPresent()) {
                data = processRawData(tmp.get());
                this.cache.put(locationName, data);
            }
        }
        return data;
    }

    /**
     * Get data as Json.
     *
     * @param locationName name of location (city)
     * @return data as json string
     */
    public Optional<String> getDataAsJson(String locationName) {
        List<DataContainer> data = getOrFetch(locationName);
        if (data.isEmpty()) {
            System.out.println("unknown location: " + locationName);
            return Optional.empty();
        }
        return Optional.of(convertToJson(data).toString());

    }

    /**
     * Convert data to the JSON.
     *
     * @param data data
     * @return maybe JSON representation.
     */
    JSONArray convertToJson(List<DataContainer> data) {
        JSONArray ja = new JSONArray();
        for (DataContainer dc : data) {
            ja.put(dc.getAsJson());
        }
        return ja;
    }

    /**
     * Get data from cache or API and returns them as HTML string.
     *
     * @param locationName name of location (city)
     * @return data as html string
     */
    public Optional<String> getDataAsHtml(String locationName) {
        List<DataContainer> data = getOrFetch(locationName);
        if (data.isEmpty()) {
            System.out.println("processing of Raw Data failed");
            return Optional.empty();
        }
        return convertToHtml(data);
    }

    /**
     * Convert data to the HTML.
     *
     * @param data data
     * @return maybe HTML representation.
     */
    Optional<String> convertToHtml(List<DataContainer> data) {
        StringBuilder sb = new StringBuilder();
        for (DataContainer dc : data) {
            sb.append(dc.getAsSimpleHtml());
        }
        return Optional.of(sb.toString());
    }

    /**
     * Should return weather data for received location
     *
     * @param rawData data from api as String
     * @return data
     */
    List<DataContainer> processRawData(String rawData) {
        List<DataContainer> dataContainerList = new LinkedList<>();

        if (rawData == null || rawData.isEmpty()) {
            return dataContainerList;
        }

        JSONObject jsonData;
        JSONObject jsonUnits;

        // get data
        try {
            jsonData = new JSONObject(rawData).getJSONObject("hourly");
        } catch (Exception e) {
            System.out.println("could NOT find hourly - bad input data");
            e.printStackTrace();
            return new LinkedList<>();
        }
        // get unit
        try {
            jsonUnits = new JSONObject(rawData).getJSONObject("hourly_units");
        } catch (Exception e) {
            System.out.println("could NOT find hourly_units - bad input data");
            e.printStackTrace();
            return new LinkedList<>();
        }

        dataContainerList = new LinkedList<>();
        Map<SupportedParametersHourly, List<String>> helpValueMap = new HashMap<>();
        Map<SupportedParametersHourly, String> helpUnitMap = new HashMap<>();
        // set value for each hour, this will reduce calling of getStringValues
        for (SupportedParametersHourly param : SupportedParametersHourly.values()) {
            List<String> tmpValueList = this.getStringValues(param.value, jsonData);
            if (!tmpValueList.isEmpty()) {
                helpValueMap.put(param, tmpValueList);
            }
            if (jsonData.has(param.value)) {
                helpUnitMap.put(param, jsonUnits.getString(param.value));
            }
        }

        // time will be always so I use it as iterator
        for (int i = 0; i < helpValueMap.get(SupportedParametersHourly.TIME).size(); i++) {
            DataContainer.DataContainerBuilder builder = new DataContainer.DataContainerBuilder();
            for (SupportedParametersHourly parameter : SupportedParametersHourly.values()) {
                if (helpValueMap.containsKey(parameter)) {
                    builder.addData(parameter, helpValueMap.get(parameter).get(i));
                }
                if (helpUnitMap.containsKey(parameter)) {
                    builder.addUnit(parameter, helpUnitMap.get(parameter));
                }
            }
            dataContainerList.add(builder.build());
        }
        return dataContainerList;
    }

    /**
     * To extract list from JsonObject.
     *
     * @param nameOfField name of list.
     * @param jsonObject  jsonObject with that list.
     * @return List as List of Strings.
     */
    private List<String> getStringValues(String nameOfField, JSONObject jsonObject) {
        if (!jsonObject.has(nameOfField)) {
            System.out.println("unknown name of field: " + nameOfField);
            return new LinkedList<>();
        }
        return jsonObject.getJSONArray(nameOfField).toList()
                .stream().map(s -> Objects.toString(s, null)).toList();
    }

    /**
     * Fetch data from API from location.
     *
     * @param locationName name of city.
     * @return maybe json from API as String.
     */
    private Optional<String> getDataFromWeb(String locationName) {
        if (!this.locationMap.containsKey(locationName)) {
            System.out.println("unknown location: " + locationName);
            return Optional.empty();
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(this.locationMap.get(locationName).Api(), String.class);
            if (result != null && !result.isEmpty()) {
                return Optional.of(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
