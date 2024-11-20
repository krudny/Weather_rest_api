package pl.codibly.weatherApp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {

    private double latitude;
    private double longitude;

    @JsonProperty("hourly")
    private HourlyData hourly;

    @JsonProperty("daily")
    private DailyData daily;

    @Getter
    @Setter
    public static class HourlyData {
        @JsonProperty("time")
        private List<String> time;

        @JsonProperty("temperature_2m")
        private List<Double> temperature2m;

    }

    @Getter
    @Setter
    public static class DailyData {
        @JsonProperty("time")
        private List<String> time;

        @JsonProperty("weather_code")
        private List<Integer> weatherCode;

        @JsonProperty("sunshine_duration")
        private List<Double> sunshineDuration;
    }
}