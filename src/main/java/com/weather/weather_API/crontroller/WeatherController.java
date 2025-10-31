package com.weather.weather_API.crontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.weather_API.model.Weather;
import com.weather.weather_API.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Value("${API_KEY}")
    private String apiKey;

    @Value("${app.api.url}")
    private String apiUrl;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("{country}")
    public Weather weather(@PathVariable String country) throws JsonProcessingException {
        String url = apiUrl + country + "?key=" + apiKey;

        return weatherService.weather(url);
    }
}
