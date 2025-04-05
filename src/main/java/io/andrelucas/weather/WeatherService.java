package io.andrelucas.weather;

import org.springframework.stereotype.Service;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class WeatherService  {
    
    
    private final WeatherIntegration weatherIntegration;
    
    public WeatherService(@Qualifier("openMeteoWeatherService") final WeatherIntegration weatherIntegration) {
        this.weatherIntegration = weatherIntegration;
    }

    @Tool(description = "Get weather forecast for a specific latitude/longitude")
    public WeatherForecast getWeatherForecastByLocation(double latitude, double longitude) {
        return weatherIntegration.getWeatherForecastByLocation(latitude, longitude);
    }
}
