package pl.codibly.weatherApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.codibly.weatherApp.apiService.ExternalApiService;
import pl.codibly.weatherApp.service.ApiService;

import java.time.LocalDate;
import java.util.Set;

@RestController
public class ApiController {

    private ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/weekly_forecast")
    public Set<LocalDate> getWeeklyForecast() {
        return apiService.getWeeklyForecast(52.52, 13.41);
    }
}
