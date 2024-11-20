package pl.codibly.weatherApp.controller;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<LocalDate, Map<String, Object>>> getWeeklyForecast(
            @RequestParam(value = "latitude") double latitude,
            @RequestParam(value = "longitude") double longitude) {

        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            Map<LocalDate, Map<String, Object>> forecast = apiService.getWeeklyForecast(latitude, longitude);

            if (forecast == null || forecast.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(forecast);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/weekly_summary")
    public ResponseEntity<Map<String, Object>> getWeeklySummary(
            @RequestParam(value = "latitude") double latitude,
            @RequestParam(value = "longitude") double longitude
    ) {

        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            Map<String, Object> forecast = apiService.getWeeklySummary(latitude, longitude);

            if (forecast == null || forecast.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(forecast);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
