package com.weatherApp.weather.util;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DataContainer {

    private final Map<SupportedParametersHourly, String> valueMap;
    private final Map<SupportedParametersHourly, String> unitMap;

    public DataContainer(Map<SupportedParametersHourly, String> valueMap,
                         Map<SupportedParametersHourly, String> unitMap) {
        this.valueMap = valueMap;
        this.unitMap = unitMap;
    }

    public String getValue(SupportedParametersHourly key) {
        return this.valueMap.get(key);
    }

    public String getUnit(SupportedParametersHourly key) {
        return this.unitMap.get(key);
    }

    /**
     * Get JSON representation.
     *
     * @return JSON representation of this instance.
     */
    public JSONObject getAsJson() {
        JSONObject jo = new JSONObject();
        this.valueMap.forEach((key, value) -> {
            JSONObject tmp = new JSONObject();
            tmp.append(key.value, value);
            jo.append(this.valueMap.get(SupportedParametersHourly.TIME), tmp);
        });
        return jo;
    }

    /**
     * Get HTML representation.
     * todo: this is ugly, make it as some HTML element object
     *
     * @return HTML representation of this instance.
     */
    public String getAsSimpleHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"hourforcast\">");
        this.valueMap.forEach((key, value) -> {
            sb.append("<div>");
            sb.append("<label for=\"")
                    .append(this.valueMap.get(SupportedParametersHourly.TIME))
                    .append("_")
                    .append(key.value)
                    .append("\">")
                    .append(key.value)
                    .append(": </label>");
            sb.append("<span id=\"")
                    .append(this.valueMap.get(SupportedParametersHourly.TIME))
                    .append("_")
                    .append(key.value)
                    .append("\">")
                    .append(value)
                    .append(" </span>");
            sb.append("<span id=\"")
                    .append(this.valueMap.get(SupportedParametersHourly.TIME))
                    .append("_").append(key.value)
                    .append("_unit\">")
                    .append(this.unitMap.get(key))
                    .append("</span>");
            sb.append("</div>");
        });
        sb.append("</div>");
        return sb.toString();
    }


    public static class DataContainerBuilder {
        private final Map<SupportedParametersHourly, String> valueMap = new HashMap<>();
        private final Map<SupportedParametersHourly, String> unitMap = new HashMap<>();

        public DataContainerBuilder() {
        }

        public void addUnit(SupportedParametersHourly key, String value) {
            this.unitMap.put(key, value);
        }

        public void addData(SupportedParametersHourly key, String value) {
            this.valueMap.put(key, value);
        }

        public DataContainer build() {
            return new DataContainer(this.valueMap, this.unitMap);
        }
    }

}
