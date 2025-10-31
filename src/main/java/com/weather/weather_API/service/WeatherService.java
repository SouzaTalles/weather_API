package com.weather.weather_API.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.weather_API.model.Weather;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class WeatherService {
    public Weather weather(String url) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> stringStringMap = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});

        @SuppressWarnings("unchecked")
        Map<String, Object> firstDay = ((List<Map<String, Object>>) stringStringMap.get("days")).get(0);

        String address = (String) stringStringMap.get("address");
        String description = (String) stringStringMap.get("description");
        String dateTime = (String) firstDay.get("datetime");
        double temp = (double) firstDay.get("temp");
        double tempmax = (double) firstDay.get("tempmax");
        double tempmin = (double) firstDay.get("tempmin");

        Weather weather = new Weather(dateTime, description, address, temp, tempmax, tempmin);

        return weather;
    }
}
