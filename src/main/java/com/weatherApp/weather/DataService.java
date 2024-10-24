package com.weatherApp.weather;

import java.util.Optional;


public interface DataService {

    Optional<String> getDataAsJson(String locationName);

    Optional<String> getDataAsHtml(String locationName);

}
