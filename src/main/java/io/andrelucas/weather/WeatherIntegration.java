package io.andrelucas.weather;

public interface WeatherIntegration {

    WeatherForecast getWeatherForecastByLocation(double latitude, double longitude);

}
