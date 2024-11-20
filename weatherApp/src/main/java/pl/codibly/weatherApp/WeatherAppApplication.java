package pl.codibly.weatherApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class WeatherAppApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(WeatherAppApplication.class, args);



	}

}
