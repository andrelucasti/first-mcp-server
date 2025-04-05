package io.andrelucas.weather.integration;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.andrelucas.weather.WeatherForecast;
import io.andrelucas.weather.WeatherIntegration;
import io.andrelucas.weather.model.OpenMeteoResponse;

@Service("openMeteoWeatherService")
public class OpenMeteoWeatherService implements WeatherIntegration {
    
    private final RestTemplate restTemplate;

    public OpenMeteoWeatherService() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public WeatherForecast getWeatherForecastByLocation(double latitude, double longitude) {
        String url = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("api.open-meteo.com")
            .path("/v1/forecast")
            .queryParam("latitude", latitude)
            .queryParam("longitude", longitude)
            .queryParam("current", "temperature_2m,wind_speed_10m,wind_direction_10m")
            .build()
            .toUriString();

        OpenMeteoResponse response = restTemplate.getForObject(url, OpenMeteoResponse.class);
        
        return new WeatherForecast(
            (int) Math.round(response.getCurrent().getTemperature2m()),
            "C", // Open-Meteo uses Celsius by default
            response.getCurrent().getWindSpeed10m() + " km/h",
            getWindDirection(response.getCurrent().getWindDirection10m()),
            String.format("Temperature: %.1f°C, Wind: %.1f km/h", 
                response.getCurrent().getTemperature2m(),
                response.getCurrent().getWindSpeed10m())
        );
    }

    private String getWindDirection(double degrees) {
        String[] directions = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
        int index = (int) Math.round(((degrees % 360) / 22.5)) % 16;
        return directions[index];
    }
} 