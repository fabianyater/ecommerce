package com.pruebatecnica.ecommerce.controller;

import com.pruebatecnica.ecommerce.dto.response.WeatherResponse;
import com.pruebatecnica.ecommerce.service.IWeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weather")
@RequiredArgsConstructor
@Tag(name = "Weather", description = "Weather API")
public class WeatherController {
    private final IWeatherService weatherService;

    @Operation(
            summary = "Get weather by city",
            description = "This method allows the retrieval of the weather information for a given city."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weather information retrieved", content = @io.swagger.v3.oas.annotations.media.Content),
            @ApiResponse(responseCode = "404", description = "City not found", content = @io.swagger.v3.oas.annotations.media.Content),
    })
    @GetMapping("/")
    public ResponseEntity<WeatherResponse> getWeather(
            @Parameter(description = "City name", required = true)
            @PathParam("city") String city) {
        return ResponseEntity.ok(weatherService.getWeather(city));
    }

    @Operation(
            summary = "Get weather by coordinates",
            description = "This method allows the retrieval of the weather information for a given set of coordinates."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weather information retrieved", content = @io.swagger.v3.oas.annotations.media.Content),
            @ApiResponse(responseCode = "404", description = "Coordinates not found", content = @io.swagger.v3.oas.annotations.media.Content),
    })
    @GetMapping("/location")
    public ResponseEntity<WeatherResponse> getWeatherByCoordinates(
            @Parameter(description = "Latitude", required = true)
            @PathParam("lat") String lat,
            @Parameter(description = "Longitude", required = true)
            @PathParam("lon") String lon) {
        return ResponseEntity.ok(weatherService.getWeatherByCoordinates(lat, lon));
    }
}
