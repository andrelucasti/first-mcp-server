package io.andrelucas.weather.integration;

import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;

import io.andrelucas.weather.WeatherForecast;
import io.andrelucas.weather.WeatherIntegration;

import org.springframework.web.client.RestClient;
import org.springframework.stereotype.Service;
@Service("weatherApiGov")
public class WeatherApiGov implements WeatherIntegration {

    private final RestClient restClient;

    public WeatherApiGov() {
        this.restClient = RestClient.builder()
        .baseUrl("https://api.weather.gov")
        .defaultHeader("Accept", "application/geo+json")
        .defaultHeader("User-Agent", "WeatherApiClient/1.0 (your@email.com)")
        .build();
    }

    @Override
    public WeatherForecast getWeatherForecastByLocation(double latitude, double longitude) {
       
        // First, get the forecast URL from the points endpoint
        ResponseEntity<JsonNode> pointsResponse = restClient.get()
            .uri("/points/{latitude},{longitude}", latitude, longitude)
            .retrieve()
            .toEntity(JsonNode.class);

        // Extract the forecast URL from the response
        String forecastUrl = pointsResponse.getBody()
            .get("properties")
            .get("forecast")
            .asText();

        // Get the detailed forecast using the obtained URL
        ResponseEntity<JsonNode> forecastResponse = restClient.get()
            .uri(forecastUrl)
            .retrieve()
            .toEntity(JsonNode.class);

        // Extract and return the relevant forecast information
        JsonNode properties = forecastResponse.getBody().get("properties");
        JsonNode periods = properties.get("periods").get(0); // Get the first period (current forecast)

        return new WeatherForecast(
            periods.get("temperature").asInt(),
            periods.get("temperatureUnit").asText(),
            periods.get("windSpeed").asText(),
            periods.get("windDirection").asText(),
            periods.get("detailedForecast").asText()
        );
    }

}
