package com.example.weather;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<?> getWeather(@RequestParam @NotBlank String city){
        try{
            String weatherData = weatherService.getWeather(city);
            return ResponseEntity.ok(weatherData);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: "+e.getMessage());
        }
    }
}
