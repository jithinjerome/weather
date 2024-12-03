package com.example.weather;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${cache.expiration}")
    private int cacheExpiration;

    @Autowired
    private RedisCacheManager redisCacheManager;


    private final RestTemplate restTemplate = new RestTemplate();

    public String getWeather(@NotBlank String city) throws Exception {
        String cachedWeather  = redisCacheManager.getFromCache(city);
        if(cachedWeather != null){
            return cachedWeather;
        }
        String url = String.format("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s?key=%s",
                city, apiKey);

        try{
            String weatherData = restTemplate.getForObject(url,String.class);
            redisCacheManager.saveToCache(city, weatherData, cacheExpiration);
            return weatherData;

        }catch (Exception e){
            throw new Exception("Failed to fetch weather data from API");

        }
    }
}
