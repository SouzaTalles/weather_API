package com.weather.weather_API.crontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WeatherController {

    @GetMapping("/weather")
    Map<String,String> weather(@RequestParam(value = "city", defaultValue = "Desconhecida") String city){
        return Map.of(
                "city", city,
                "temperature", "25°C",
                "condition", "Parcialmente nublado",
                "humidity", "60%",
                "wind_speed", "10 km/h"
        );
    }
}
