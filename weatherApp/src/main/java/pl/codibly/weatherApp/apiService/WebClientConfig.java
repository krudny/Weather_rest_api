package pl.codibly.weatherApp.apiService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.rmi.ServerException;
import java.rmi.UnexpectedException;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://api.open-meteo.com/v1/forecast")
                .filter(handleError())
                .build();
    }

    private ExchangeFilterFunction handleError() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().is4xxClientError()) {
                return Mono.error(new UnexpectedException("Client error: " + clientResponse.statusCode()));
            } else if (clientResponse.statusCode().is5xxServerError()) {
                return Mono.error(new ServerException("Server error: " + clientResponse.statusCode()));
            }
            return Mono.just(clientResponse);
        });
    }
}
