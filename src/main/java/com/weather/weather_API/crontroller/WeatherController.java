package com.weather.weather_API.crontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.weather.weather_API.model.Weather;
import com.weather.weather_API.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

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
    @Cacheable(value = "weather_single", key = "#country")
    public Weather weather(@PathVariable String country) throws JsonProcessingException {
        String url = apiUrl + country + "?key=" + apiKey + "&lang=pt";

        return weatherService.weather(url);
    }
}
