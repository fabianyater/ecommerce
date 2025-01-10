package com.pruebatecnica.ecommerce.service.impl;

import com.pruebatecnica.ecommerce.dto.response.WeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    WeatherServiceImpl weatherService;

    private final String apiKey = "apiKey";
    private final String apiUrl = "https://api.openweathermap.org/data/2.5/weather";

    @BeforeEach
    void setup() {
        restTemplate = mock(RestTemplate.class);
        weatherService = new WeatherServiceImpl(restTemplate);

        setPrivateField(weatherService, "apiKey", apiKey);
        setPrivateField(weatherService, "apiUrl", apiUrl);
    }

    @Test
    void testGetWeather() {
        String city = "London";
        String url = String.format("%s?q=%s&appid=%s", apiUrl, city, apiKey);

        WeatherResponse mockResponse = new WeatherResponse();
        mockResponse.setName(city);
        ResponseEntity<WeatherResponse> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(url, WeatherResponse.class)).thenReturn(responseEntity);

        WeatherResponse result = weatherService.getWeather(city);

        assertNotNull(result);
        assertEquals(city, result.getName());
        verify(restTemplate, times(1)).getForEntity(url, WeatherResponse.class);
    }

    @Test
    void testGetWeatherByCoordinates() {
        String lat = "51.5074";
        String lon = "-0.1278";
        String url = String.format("%s?lat=%s&lon=%s&appid=%s", apiUrl, lat, lon, apiKey);

        WeatherResponse mockResponse = new WeatherResponse();
        WeatherResponse.Coord coord = new WeatherResponse.Coord();
        coord.setLat(Double.parseDouble(lat));
        coord.setLon(Double.parseDouble(lon));
        mockResponse.setCoord(coord);

        ResponseEntity<WeatherResponse> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(url, WeatherResponse.class)).thenReturn(responseEntity);

        WeatherResponse result = weatherService.getWeatherByCoordinates(lat, lon);

        assertNotNull(result);
        assertNotNull(result.getCoord());
        assertEquals(Double.parseDouble(lat), result.getCoord().getLat());
        assertEquals(Double.parseDouble(lon), result.getCoord().getLon());
        verify(restTemplate, times(1)).getForEntity(url, WeatherResponse.class);
    }

    @Test
    void testGetWeatherNotFound() {
        String city = "InvalidCity";
        String url = String.format("%s?q=%s&appid=%s", apiUrl, city, apiKey);

        when(restTemplate.getForEntity(url, WeatherResponse.class)).thenThrow(new RuntimeException("City not found"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> weatherService.getWeather(city));
        assertEquals("City not found", exception.getMessage());

        verify(restTemplate, times(1)).getForEntity(url, WeatherResponse.class);
    }


    private void setPrivateField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}