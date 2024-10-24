package com.weatherApp.weather;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class MyController {

    private final DataService dataService;


    public MyController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/data")
    public String index(@RequestParam(name = "location", required = false, defaultValue = "unknown") String location,
                        Model model) {
        Optional<String> htmlOutput = dataService.getDataAsHtml(location);

        if (htmlOutput.isPresent()) {
            model.addAttribute("name", location);
            model.addAttribute("injectToHtml", htmlOutput.get());
            return "data";
        } else {
            return "error";
        }
    }

}
