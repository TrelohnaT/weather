package com.weatherApp.weather;

import java.util.Optional;


public interface DataService {

    Optional<String> fetchDataGetAsJson(String locationName);

    Optional<String> fetchDataGetAsHtml(String locationName);

}
