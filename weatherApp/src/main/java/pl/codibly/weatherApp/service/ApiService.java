package pl.codibly.weatherApp.service;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import pl.codibly.weatherApp.DTO.WeatherResponse;
import pl.codibly.weatherApp.apiService.ExternalApiService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ApiService {
    private ExternalApiService externalApiService;

    public ApiService(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    private Set<LocalDate> getDates(WeatherResponse response) {
        Set<LocalDate> uniqueDates = response
                .getHourly()
                .getTime()
                .stream()
                .map(dateTimeString -> LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .map(LocalDateTime::toLocalDate)
                .collect(Collectors.toSet());

        return uniqueDates;
    }


    public Set<LocalDate> getWeeklyForecast(double latitude, double longitude) {
        WeatherResponse response = externalApiService.getWeatherForecast(latitude, longitude).block();

        return getDates(response);
    }
}
