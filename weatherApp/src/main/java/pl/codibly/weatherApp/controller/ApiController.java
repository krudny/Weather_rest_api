package pl.codibly.weatherApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.codibly.weatherApp.service.ApiService;

import java.time.LocalDate;
import java.util.Map;

@RestController
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/weekly_forecast")
    public Map<LocalDate, Map<String, Object>> getWeeklyForecast(
            @RequestParam(value = "latitude", defaultValue = "52.52") double latitude,
            @RequestParam(value = "longitude", defaultValue = "13.41") double longitude) {
        return apiService.getWeeklyForecast(latitude, longitude);
    }

    @GetMapping("/weekly_summary")
    public Map<String, Object> getWeeklySummary() {
        return apiService.getWeeklySummary(52.52, 13.41);
    }
}
