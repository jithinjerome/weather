package com.example.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(weatherData);
            String resolvedCity = rootNode.get("address").asText();
            double temperature = rootNode.get("days").get(0).get("temp").asDouble();
            String condition = rootNode.get("days").get(0).get("conditions").asText();
            double feelsLike = rootNode.get("days").get(0).get("feelslike").asDouble();

            WeatherDTO response = new WeatherDTO(resolvedCity,temperature, condition, feelsLike);
            //String response = String.format("City: %-20s\nTemperature: %-10.2f °C\nCondition: %-25s\nFeels Like: %-10.2f °C", resolvedCity, temperature, condition, feelsLike);


            redisCacheManager.saveToCache(city, mapper.writeValueAsString(response), cacheExpiration);

            return mapper.writeValueAsString(response);

        }catch (Exception e){
            throw new Exception("Failed to fetch weather data from API: "+e.getMessage(),e);

        }
    }
}
