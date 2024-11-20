package pl.codibly.weatherApp.service;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import pl.codibly.weatherApp.DTO.WeatherResponse;
import pl.codibly.weatherApp.apiService.ExternalApiService;
import pl.codibly.weatherApp.util.WeatherCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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



    public Map<LocalDate, Map<String, Object>> getWeeklyForecast(double latitude, double longitude) {
        WeatherResponse response = externalApiService.getWeatherForecast(latitude, longitude).block();

        Map<LocalDate, Map<String, Object>> result = new HashMap<>();

        List<LocalDate> dates = getDates(response);

        for(int i = 0; i < dates.size(); i++) {
            Map<String, Object> dailyData = new HashMap<>();
            dailyData.put("Min temperature", response.getDaily().getMinTemperature().get(i));
            dailyData.put("Max temperature", response.getDaily().getMaxTemperature().get(i));
            int weatherCode = response.getDaily().getWeatherCode().get(i);
            dailyData.put("Weather code", weatherCode + " " + WeatherCode.getDescriptionByCode(weatherCode));
            int sunshine_duration = response.getDaily().getSunshineDuration().get(i);
            dailyData.put("Energy produced", calculateEnergyProduction(sunshine_duration) + "KWh");

            result.put(dates.get(i), dailyData);
        }

        return result;

    }


}
