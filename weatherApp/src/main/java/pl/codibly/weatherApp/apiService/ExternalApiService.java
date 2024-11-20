package pl.codibly.weatherApp.apiService;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.codibly.weatherApp.DTO.WeatherResponse;
import reactor.core.publisher.Mono;

@Service
public class ExternalApiService {
    private final WebClient webClient;

    public ExternalApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<WeatherResponse> getWeatherForecast(double latitude, double longitude) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam("hourly", "temperature_2m")
                        .queryParam("daily", "weather_code,sunshine_duration,temperature_2m_max,temperature_2m_min")
                        .build())
                .retrieve()
                .bodyToMono(WeatherResponse.class);
    }
}
