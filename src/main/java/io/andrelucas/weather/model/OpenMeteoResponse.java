package io.andrelucas.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenMeteoResponse {
    private Current current;

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public static class Current {
        private double temperature2m;
        private double windSpeed10m;
        private double windDirection10m;
        private String time;

        @JsonProperty("temperature_2m")
        public double getTemperature2m() {
            return temperature2m;
        }

        @JsonProperty("temperature_2m")
        public void setTemperature2m(double temperature2m) {
            this.temperature2m = temperature2m;
        }

        @JsonProperty("wind_speed_10m")
        public double getWindSpeed10m() {
            return windSpeed10m;
        }

        @JsonProperty("wind_speed_10m")
        public void setWindSpeed10m(double windSpeed10m) {
            this.windSpeed10m = windSpeed10m;
        }

        @JsonProperty("wind_direction_10m")
        public double getWindDirection10m() {
            return windDirection10m;
        }

        @JsonProperty("wind_direction_10m")
        public void setWindDirection10m(double windDirection10m) {
            this.windDirection10m = windDirection10m;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
} 