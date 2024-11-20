package pl.codibly.weatherApp.service;

import org.springframework.stereotype.Service;
import pl.codibly.weatherApp.DTO.WeatherResponse;
import pl.codibly.weatherApp.apiService.ExternalApiService;
import pl.codibly.weatherApp.util.WeatherCode;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiService {
    private final ExternalApiService externalApiService;

    public ApiService(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    private WeatherResponse getAndValidateResponse(double latitude, double longitude) {
        WeatherResponse response = externalApiService.getWeatherForecast(latitude, longitude).block();

        if (response == null || response.getDaily() == null || response.getHourly() == null) {
            throw new RuntimeException("Fetched nullable data");
        }

        return response;
    }

    private List<LocalDate> getDates(WeatherResponse response) {
        return response
                .getDaily()
                .getTime().stream()
                .map(LocalDate::parse)
                .toList();
    }

    private Double calculateEnergyProduction(int sunshineDuration) {
        return sunshineDuration / 3600.0 * 2.5 * 0.2;
    }

    private Double calculateAverage(List<Double> values) {
        return values.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    private String checkRain(List<Integer> weatherCodes) {
        long rainyDays = weatherCodes.stream()
                .filter(code -> code > 50)
                .count();
        return rainyDays > 4 ? "rainy" : "not rainy";
    }

    public Map<LocalDate, Map<String, Object>> getWeeklyForecast(double latitude, double longitude) {
        WeatherResponse response;
        try {
             response = getAndValidateResponse(latitude, longitude);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching weather data from external API", e);
        }

        List<LocalDate> dates = getDates(response);

        return dates.stream().collect(Collectors.toMap(
                date -> date,
                date -> {
                    int index = dates.indexOf(date);
                    int weatherCode = response.getDaily().getWeatherCodes().get(index);
                    int sunshineDuration = response.getDaily().getSunshineDuration().get(index).intValue();

                    Map<String, Object> dailyData = new HashMap<>();
                    dailyData.put("Min temperature", response.getDaily().getMinTemperature().get(index));
                    dailyData.put("Max temperature", response.getDaily().getMaxTemperature().get(index));
                    dailyData.put("Weather code", weatherCode + " " + WeatherCode.getDescriptionByCode(weatherCode));
                    dailyData.put("Energy produced", String.format("%.2f h", calculateEnergyProduction(sunshineDuration)) + " kWh");
                    return dailyData;
                }
        ));
    }

    public Map<String, Object> getWeeklySummary(double latitude, double longitude) {
        WeatherResponse response;
        try {
            response = getAndValidateResponse(latitude, longitude);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching weather data from external API", e);
        }

        List<Double> hourlyTemperatures = response.getHourly().getTemperature();
        List<Double> dailySunshineDurations = response.getDaily().getSunshineDuration();

        Map<String, Object> result = new HashMap<>();
        result.put("Average surface pressure", String.format("%.2f h", calculateAverage(response.getHourly().getSurface_pressure())) + " hPa");
        result.put("Average sunshine duration", String.format("%.2f h", calculateAverage(dailySunshineDurations) / 36));
        result.put("Weekly minimum temperature", Collections.min(hourlyTemperatures));
        result.put("Weekly maximum temperature", Collections.max(hourlyTemperatures));
        result.put("Overall summary", checkRain(response.getDaily().getWeatherCodes()));

        return result;
    }
}
