package com.weatherApp.weather.util;

public class Location {

    private String name = "";
    private double latitude = 0;
    private double longitude = 0;

    public Location(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String Name() {
        return this.name;
    }

    public double Latitude() {
        return this.latitude;
    }

    public double Longitude() {
        return this.longitude;
    }

    public String Api() {
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.open-meteo.com/v1/forecast?latitude=")
                .append(this.latitude).append("&longitude=").append(this.longitude).append("&hourly=");
        sb.append(SupportedParametersHourly.getSuffixForApi());
        return sb.toString();
    }

}
