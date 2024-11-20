package pl.codibly.weatherApp.apiService;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
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
                        .queryParam("hourly", "temperature_2m,surface_pressure")
                        .queryParam("daily", "weather_code,sunshine_duration,temperature_2m_max,temperature_2m_min")
                        .build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response -> {
                    return Mono.error(new RuntimeException("Error during fetching: " + response.statusCode()));
                })
                .bodyToMono(WeatherResponse.class)
                .onErrorMap(WebClientResponseException.class, e -> {
                    return new RuntimeException("Webclient error: " + e.getMessage(), e);
                })
                .onErrorResume(e -> {
                    return Mono.error(new RuntimeException("Unexpected error", e));
                });
    }

}
