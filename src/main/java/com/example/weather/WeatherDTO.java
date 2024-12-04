package com.example.weather;

import lombok.Data;

@Data
public class WeatherDTO {

    private String resolvedCity;
    private double temperature;
    private String condition;
    private double feelsLike;

    public WeatherDTO() {}


    public WeatherDTO(String resolvedCity, double temperature, String condition, double feelsLike) {
        this.resolvedCity = resolvedCity;
        this.temperature = temperature;
        this.condition = condition;
        this.feelsLike = feelsLike;
    }
}
