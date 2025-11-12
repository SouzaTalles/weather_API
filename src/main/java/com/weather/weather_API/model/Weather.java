package com.weather.weather_API.model;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather implements Serializable {
    private String dateTime;
    private String description;
    private String address;
    private double temperature;
    private double tempmax;
    private double tempmin;
}
