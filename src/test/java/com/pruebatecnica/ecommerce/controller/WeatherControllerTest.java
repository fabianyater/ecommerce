package com.pruebatecnica.ecommerce.controller;

import com.pruebatecnica.ecommerce.dto.response.WeatherResponse;
import com.pruebatecnica.ecommerce.service.IWeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WeatherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IWeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }

    @Test
    void testGetWeather() throws Exception {
        String city = "London";
        WeatherResponse.Main main = new WeatherResponse.Main();
        main.setTemp(15.0);
        WeatherResponse mockResponse = new WeatherResponse();
        mockResponse.setName(city);
        mockResponse.setMain(main);

        when(weatherService.getWeather(city)).thenReturn(mockResponse);

        mockMvc.perform(
                        get("/api/v1/weather/")
                                .param("city", city)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(city))
                .andExpect(jsonPath("$.main.temp").value(15.0));

        verify(weatherService).getWeather(city);
    }

    @Test
    void testGetWeatherByCoordinates() throws Exception {
        String lat = "51.51";
        String lon = "-0.13";
        WeatherResponse.Main main = new WeatherResponse.Main();
        main.setTemp(15.0);
        WeatherResponse mockResponse = new WeatherResponse();
        mockResponse.setName("London");
        mockResponse.setMain(main);

        when(weatherService.getWeatherByCoordinates(lat, lon)).thenReturn(mockResponse);

        mockMvc.perform(
                        get("/api/v1/weather/location")
                                .param("lat", lat)
                                .param("lon", lon)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("London"))
                .andExpect(jsonPath("$.main.temp").value(15.0));

        verify(weatherService).getWeatherByCoordinates(lat, lon);
    }


}