package io.andrelucas.weather;

// Create a record to hold the forecast data
public record WeatherForecast(
    int temperature,
    String temperatureUnit,
    String windSpeed,
    String windDirection,
    String detailedForecast
) {}