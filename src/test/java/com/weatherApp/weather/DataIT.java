package com.weatherApp.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DataIT {

    private final DataService dataService;

    @Autowired
    public DataIT(DataService dataService) {
        this.dataService = dataService;
    }

    @Test
    void getHtmlExistingCity() {
        assertTrue(dataService.fetchDataGetAsHtml("ostrava").isPresent());
    }

    @Test
    void getHtmlNonExistingCity() {
        assertFalse(dataService.fetchDataGetAsHtml("tralaland").isPresent());
    }

    @Test
    void getJsonExistingCity() {
        assertTrue(dataService.fetchDataGetAsJson("ostrava").isPresent());
    }

    @Test
    void getJsonNonExistingCity() {
        assertFalse(dataService.fetchDataGetAsJson("tralaland").isPresent());
    }

}
