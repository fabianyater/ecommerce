package com.pruebatecnica.ecommerce.service.impl;

import com.pruebatecnica.ecommerce.dto.response.WeatherResponse;
import com.pruebatecnica.ecommerce.service.IWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements IWeatherService {
    private final RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Override
    public WeatherResponse getWeather(String city) {
        String url = String.format("%s?q=%s&appid=%s", apiUrl, city, apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(url, WeatherResponse.class);

        return response.getBody();
    }

    @Override
    public WeatherResponse getWeatherByCoordinates(String lat, String lon) {
        String url = String.format("%s?lat=%s&lon=%s&appid=%s", apiUrl, lat, lon, apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(url, WeatherResponse.class);

        return response.getBody();
    }
}
