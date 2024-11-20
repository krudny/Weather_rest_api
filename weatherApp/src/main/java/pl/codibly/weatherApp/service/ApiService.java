package pl.codibly.weatherApp.service;

import org.springframework.stereotype.Service;
import pl.codibly.weatherApp.DTO.WeatherResponse;
import pl.codibly.weatherApp.apiService.ExternalApiService;
import pl.codibly.weatherApp.util.WeatherCode;

import java.time.LocalDate;
import java.util.*;

@Service
public class ApiService {
    private ExternalApiService externalApiService;

    public ApiService(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    private List<LocalDate> getDates(WeatherResponse response) {
        return response
                .getDaily()
                .getTime()
                .stream()
                .map(LocalDate::parse)
                .toList();
    }

    private Object calculateEnergyProduction(int sunshineDuration) {
        return sunshineDuration / 3600 * 2.5 * 0.2;
    }

    private Double calculateAverage(List<Double> values) {
        double average = values.stream()
                            .mapToDouble(Double::doubleValue)
                            .average()
                            .orElse(0.0);
        return Math.round(average * 100.0) / 100.0;
    }

    private String checkRain(List<Integer> weatherCodes) {
        int sum = 0;
        for (Integer weatherCode : weatherCodes) {
            sum += weatherCode;
        }

        return sum > weatherCodes.size() * 50 ? "rainy" : "not rainy";
    }




    public Map<LocalDate, Map<String, Object>> getWeeklyForecast(double latitude, double longitude) {
        WeatherResponse response = externalApiService.getWeatherForecast(latitude, longitude).block();

        Map<LocalDate, Map<String, Object>> result = new HashMap<>();

        List<LocalDate> dates = getDates(response);

        for(int i = 0; i < dates.size(); i++) {
            Map<String, Object> dailyData = new HashMap<>();
            dailyData.put("Min temperature", response.getDaily().getMinTemperature().get(i));
            dailyData.put("Max temperature", response.getDaily().getMaxTemperature().get(i));
            int weatherCode = response.getDaily().getWeatherCodes().get(i);
            dailyData.put("Weather code", weatherCode + " " + WeatherCode.getDescriptionByCode(weatherCode));
            int sunshine_duration = response.getDaily().getSunshineDuration().get(i).intValue();
            dailyData.put("Energy produced", calculateEnergyProduction(sunshine_duration) + "KWh");

            result.put(dates.get(i), dailyData);
        }

        return result;
    }

    public Map<String, Object> getWeeklySummary(double latitude, double longitude) {
        WeatherResponse response = externalApiService.getWeatherForecast(latitude, longitude).block();
        Map<String, Object> result = new HashMap<>();
        result.put("Average surface pressure", calculateAverage(response.getHourly().getSurface_pressure()) + "hPa");
        result.put("Average sunshine duration", Math.round(calculateAverage(response.getDaily().getSunshineDuration()) / 36) / 100.0 + "h");
        result.put("Weekly minimum temperature", Collections.min(response.getHourly().getTemperature()));
        result.put("Weekly maximum temperature", Collections.max(response.getHourly().getTemperature()));
        result.put("Overall summary", checkRain(response.getDaily().getWeatherCodes()));

        return result;
    }
}
