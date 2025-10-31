package com.weather.weather_API.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
    private String dateTime;
    private String description;
    private String address;
    private double temperature;
    private double tempmax;
    private double tempmin;
}
