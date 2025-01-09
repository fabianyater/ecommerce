package com.pruebatecnica.ecommerce.controller;

import com.pruebatecnica.ecommerce.dto.response.WeatherResponse;
import com.pruebatecnica.ecommerce.service.IWeatherService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final IWeatherService weatherService;

    @GetMapping("/")
    public ResponseEntity<WeatherResponse> getWeather(@PathParam("city") String city) {
        return ResponseEntity.ok(weatherService.getWeather(city));
    }

    @GetMapping("/location")
    public ResponseEntity<WeatherResponse> getWeatherByCoordinates(
            @PathParam("lat") String lat,
            @PathParam("lon") String lon) {
        return ResponseEntity.ok(weatherService.getWeatherByCoordinates(lat, lon));
    }
}
