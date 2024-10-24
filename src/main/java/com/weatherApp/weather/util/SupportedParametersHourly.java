package com.weatherApp.weather.util;

public enum SupportedParametersHourly {
    TIME("time"), // this is NOT needed in suffix for API
    TEMPERATURE_2M("temperature_2m"),
    RELATIVE_HUMIDITY_2M("relative_humidity_2m"),
    RAIN("rain"),
    SNOWFALL("snowfall"),
    WIND_SPEED_10M("wind_speed_10m");

    // do NOT forget to add new parameter also to getSuffixForApi method

    public final String value;

    SupportedParametersHourly(String value) {
        this.value = value;
    }

    /**
     * Get suffix for API call.
     * todo: this might be as loop thought fields and only exclude the TIME
     * @return suffix for API call.
     */
    public static String getSuffixForApi() {
        return TEMPERATURE_2M.value + ","
                + RELATIVE_HUMIDITY_2M.value + ","
                + RAIN.value + ","
                + SNOWFALL.value + ","
                + WIND_SPEED_10M.value;
    }

}
