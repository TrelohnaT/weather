package com.weatherApp.weather;

import com.weatherApp.weather.util.DataContainer;
import com.weatherApp.weather.util.Location;
import com.weatherApp.weather.util.SupportedParametersHourly;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DataTest {
    // after you find how to access files from test, delete this
    String rawData = "{\"latitude\":52.52,\"longitude\":13.419998,\"generationtime_ms\":0.09191036224365234,\"utc_offset_seconds\":0,\"timezone\":\"GMT\",\"timezone_abbreviation\":\"GMT\",\"elevation\":38.0,\"hourly_units\":{\"time\":\"iso8601\",\"temperature_2m\":\"°C\",\"wind_speed_10m\":\"km/h\"},\"hourly\":{\"time\":[\"2024-10-24T00:00\",\"2024-10-24T01:00\",\"2024-10-24T02:00\",\"2024-10-24T03:00\",\"2024-10-24T04:00\",\"2024-10-24T05:00\",\"2024-10-24T06:00\",\"2024-10-24T07:00\",\"2024-10-24T08:00\",\"2024-10-24T09:00\",\"2024-10-24T10:00\",\"2024-10-24T11:00\",\"2024-10-24T12:00\",\"2024-10-24T13:00\",\"2024-10-24T14:00\",\"2024-10-24T15:00\",\"2024-10-24T16:00\",\"2024-10-24T17:00\",\"2024-10-24T18:00\",\"2024-10-24T19:00\",\"2024-10-24T20:00\",\"2024-10-24T21:00\",\"2024-10-24T22:00\",\"2024-10-24T23:00\",\"2024-10-25T00:00\",\"2024-10-25T01:00\",\"2024-10-25T02:00\",\"2024-10-25T03:00\",\"2024-10-25T04:00\",\"2024-10-25T05:00\",\"2024-10-25T06:00\",\"2024-10-25T07:00\",\"2024-10-25T08:00\",\"2024-10-25T09:00\",\"2024-10-25T10:00\",\"2024-10-25T11:00\",\"2024-10-25T12:00\",\"2024-10-25T13:00\",\"2024-10-25T14:00\",\"2024-10-25T15:00\",\"2024-10-25T16:00\",\"2024-10-25T17:00\",\"2024-10-25T18:00\",\"2024-10-25T19:00\",\"2024-10-25T20:00\",\"2024-10-25T21:00\",\"2024-10-25T22:00\",\"2024-10-25T23:00\",\"2024-10-26T00:00\",\"2024-10-26T01:00\",\"2024-10-26T02:00\",\"2024-10-26T03:00\",\"2024-10-26T04:00\",\"2024-10-26T05:00\",\"2024-10-26T06:00\",\"2024-10-26T07:00\",\"2024-10-26T08:00\",\"2024-10-26T09:00\",\"2024-10-26T10:00\",\"2024-10-26T11:00\",\"2024-10-26T12:00\",\"2024-10-26T13:00\",\"2024-10-26T14:00\",\"2024-10-26T15:00\",\"2024-10-26T16:00\",\"2024-10-26T17:00\",\"2024-10-26T18:00\",\"2024-10-26T19:00\",\"2024-10-26T20:00\",\"2024-10-26T21:00\",\"2024-10-26T22:00\",\"2024-10-26T23:00\",\"2024-10-27T00:00\",\"2024-10-27T01:00\",\"2024-10-27T02:00\",\"2024-10-27T03:00\",\"2024-10-27T04:00\",\"2024-10-27T05:00\",\"2024-10-27T06:00\",\"2024-10-27T07:00\",\"2024-10-27T08:00\",\"2024-10-27T09:00\",\"2024-10-27T10:00\",\"2024-10-27T11:00\",\"2024-10-27T12:00\",\"2024-10-27T13:00\",\"2024-10-27T14:00\",\"2024-10-27T15:00\",\"2024-10-27T16:00\",\"2024-10-27T17:00\",\"2024-10-27T18:00\",\"2024-10-27T19:00\",\"2024-10-27T20:00\",\"2024-10-27T21:00\",\"2024-10-27T22:00\",\"2024-10-27T23:00\",\"2024-10-28T00:00\",\"2024-10-28T01:00\",\"2024-10-28T02:00\",\"2024-10-28T03:00\",\"2024-10-28T04:00\",\"2024-10-28T05:00\",\"2024-10-28T06:00\",\"2024-10-28T07:00\",\"2024-10-28T08:00\",\"2024-10-28T09:00\",\"2024-10-28T10:00\",\"2024-10-28T11:00\",\"2024-10-28T12:00\",\"2024-10-28T13:00\",\"2024-10-28T14:00\",\"2024-10-28T15:00\",\"2024-10-28T16:00\",\"2024-10-28T17:00\",\"2024-10-28T18:00\",\"2024-10-28T19:00\",\"2024-10-28T20:00\",\"2024-10-28T21:00\",\"2024-10-28T22:00\",\"2024-10-28T23:00\",\"2024-10-29T00:00\",\"2024-10-29T01:00\",\"2024-10-29T02:00\",\"2024-10-29T03:00\",\"2024-10-29T04:00\",\"2024-10-29T05:00\",\"2024-10-29T06:00\",\"2024-10-29T07:00\",\"2024-10-29T08:00\",\"2024-10-29T09:00\",\"2024-10-29T10:00\",\"2024-10-29T11:00\",\"2024-10-29T12:00\",\"2024-10-29T13:00\",\"2024-10-29T14:00\",\"2024-10-29T15:00\",\"2024-10-29T16:00\",\"2024-10-29T17:00\",\"2024-10-29T18:00\",\"2024-10-29T19:00\",\"2024-10-29T20:00\",\"2024-10-29T21:00\",\"2024-10-29T22:00\",\"2024-10-29T23:00\",\"2024-10-30T00:00\",\"2024-10-30T01:00\",\"2024-10-30T02:00\",\"2024-10-30T03:00\",\"2024-10-30T04:00\",\"2024-10-30T05:00\",\"2024-10-30T06:00\",\"2024-10-30T07:00\",\"2024-10-30T08:00\",\"2024-10-30T09:00\",\"2024-10-30T10:00\",\"2024-10-30T11:00\",\"2024-10-30T12:00\",\"2024-10-30T13:00\",\"2024-10-30T14:00\",\"2024-10-30T15:00\",\"2024-10-30T16:00\",\"2024-10-30T17:00\",\"2024-10-30T18:00\",\"2024-10-30T19:00\",\"2024-10-30T20:00\",\"2024-10-30T21:00\",\"2024-10-30T22:00\",\"2024-10-30T23:00\"],\"temperature_2m\":[5.3,5.3,5.6,4.4,4.0,4.2,4.3,4.1,5.6,9.0,11.4,13.4,15.0,15.3,14.8,14.0,12.6,11.4,10.6,10.1,9.7,9.2,8.8,8.2,7.8,7.5,7.3,7.0,6.7,6.7,6.6,7.2,8.9,11.0,12.9,14.5,15.8,16.6,16.8,16.0,13.9,12.5,11.7,11.1,10.6,10.0,9.5,9.0,8.6,8.1,7.7,7.3,7.0,6.8,6.4,6.9,8.4,11.2,13.6,15.3,16.5,17.8,17.9,17.2,15.4,14.1,13.2,12.5,11.8,11.2,10.8,10.3,9.9,9.6,9.4,9.1,8.9,8.6,8.4,8.7,10.0,12.0,13.9,15.1,16.1,16.6,16.7,16.2,14.7,13.5,12.6,12.1,12.0,12.0,11.9,11.9,11.7,11.5,11.1,10.6,9.8,8.9,8.4,8.7,9.3,10.1,11.0,12.0,12.7,13.0,13.0,12.7,12.0,11.1,10.2,9.7,9.3,8.9,8.3,7.6,6.9,6.1,5.2,4.4,3.8,3.2,3.1,3.5,4.3,5.5,7.7,10.4,12.4,15.4,15.2,14.8,14.3,13.6,13.1,12.8,12.7,12.6,12.4,12.3,12.1,12.1,12.1,12.1,12.1,12.1,12.3,12.6,13.1,13.6,14.2,14.9,15.3,15.3,15.1,14.8,14.6,14.2,13.9,13.4,13.0,12.6,12.3,12.2],\"wind_speed_10m\":[4.2,4.2,4.7,3.9,5.2,5.8,5.6,5.9,6.7,9.2,10.2,11.7,12.2,13.0,13.2,12.7,11.7,11.7,11.2,10.5,10.3,10.2,9.2,7.7,7.2,7.3,6.6,6.6,6.1,6.7,6.1,6.9,6.6,6.8,7.3,7.2,8.6,8.3,7.6,5.4,5.3,5.5,5.0,4.5,4.3,4.9,4.3,4.6,4.3,4.3,3.8,3.6,3.9,4.1,3.9,4.0,3.5,4.1,4.1,4.8,4.8,6.4,6.6,6.8,5.8,5.7,5.4,6.0,7.1,6.8,5.8,5.4,5.6,5.7,5.6,6.5,5.8,6.4,6.9,6.2,5.6,6.4,7.1,5.5,4.7,5.4,5.8,5.2,5.4,6.2,6.0,5.4,4.6,4.2,4.6,5.7,6.2,6.1,6.1,5.9,4.8,3.1,2.3,2.5,3.6,4.4,4.4,3.6,3.3,3.3,3.3,3.3,3.1,3.4,3.2,2.6,2.0,1.8,1.6,1.8,2.4,1.8,1.3,1.1,1.4,1.8,2.2,1.8,1.3,1.1,1.1,1.1,1.3,6.2,6.5,7.0,7.3,7.9,8.0,7.9,7.3,7.3,7.2,7.2,7.2,7.6,7.6,7.7,7.1,6.2,5.8,7.0,8.7,10.1,9.7,9.0,8.0,7.2,7.1,6.8,6.1,5.3,4.7,4.6,5.4,5.6,5.9,6.4]}}";

    @Test
    void processData() {
        DataHandler handler = new DataHandler();
        List<DataContainer> data = handler.processRawData(rawData);
        assertEquals(168, data.size());
        assertEquals("°C", data.get(42).getUnit(SupportedParametersHourly.TEMPERATURE_2M));
        // if Json from API does have not all supported parameters
        assertNull(data.get(13).getUnit(SupportedParametersHourly.RAIN));
        assertEquals("6.8", data.get(69).getValue(SupportedParametersHourly.WIND_SPEED_10M));
    }

    @Test
    void generateAsJson() {
        DataHandler handler = new DataHandler();
        List<DataContainer> data = handler.processRawData(rawData);
        JSONArray json = handler.getAsJson(data);
        assertNotEquals(null, json);
        assertEquals(168, json.toList().size());
    }

    //@Test
    void fetchLiveData() {
        Location location = new Location("ostrava", 49.84, 18.29);
        System.out.println(location.Api());
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(location.Api(), String.class);
        System.out.println(result);
    }


}
