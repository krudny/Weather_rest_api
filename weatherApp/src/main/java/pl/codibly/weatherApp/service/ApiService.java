package pl.codibly.weatherApp.service;

import org.springframework.stereotype.Service;
import pl.codibly.weatherApp.apiService.ExternalApiService;

@Service
public class ApiService {
    private ExternalApiService externalApiService;

    public ApiService(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    public String getWeeklyForecast(double latitute, double longitude) {
        return externalApiService.getWeatherForecast(latitute, longitude).block();
    }
}
