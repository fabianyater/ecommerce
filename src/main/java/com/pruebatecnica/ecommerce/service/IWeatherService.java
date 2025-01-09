package com.pruebatecnica.ecommerce.service;

import com.pruebatecnica.ecommerce.dto.response.WeatherResponse;

public interface IWeatherService {
    WeatherResponse getWeather(String city);
    WeatherResponse getWeatherByCoordinates(String lat, String lon);
}
