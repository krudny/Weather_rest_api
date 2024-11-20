package pl.codibly.weatherApp.util;

public enum WeatherCode {
    CLEAR_SKY(0, "Clear sky"),
    MAINLY_CLEAR(1, "Mainly clear"),
    PARTLY_CLOUDY(2, "Partly cloudy"),
    OVERCAST(3, "Overcast"),
    FOG(45, "Fog"),
    DEPOSITING_RIME_FOG(48, "Depositing rime fog"),
    LIGHT_DRIZZLE(51, "Light drizzle"),
    MODERATE_DRIZZLE(53, "Moderate drizzle"),
    DENSE_DRIZZLE(55, "Dense drizzle"),
    LIGHT_FREEZING_DRIZZLE(56, "Light freezing drizzle"),
    DENSE_FREEZING_DRIZZLE(57, "Dense freezing drizzle"),
    SLIGHT_RAIN(61, "Slight rain"),
    MODERATE_RAIN(63, "Moderate rain"),
    HEAVY_RAIN(65, "Heavy rain"),
    LIGHT_FREEZING_RAIN(66, "Light freezing rain"),
    HEAVY_FREEZING_RAIN(67, "Heavy freezing rain"),
    SLIGHT_SNOW_FALL(71, "Slight snow fall"),
    MODERATE_SNOW_FALL(73, "Moderate snow fall"),
    HEAVY_SNOW_FALL(75, "Heavy snow fall"),
    SNOW_GRAINS(77, "Snow grains"),
    SLIGHT_RAIN_SHOWERS(80, "Slight rain showers"),
    MODERATE_RAIN_SHOWERS(81, "Moderate rain showers"),
    VIOLENT_RAIN_SHOWERS(82, "Violent rain showers"),
    SLIGHT_SNOW_SHOWERS(85, "Slight snow showers"),
    HEAVY_SNOW_SHOWERS(86, "Heavy snow showers"),
    SLIGHT_THUNDERSTORM(95, "Slight thunderstorm"),
    MODERATE_THUNDERSTORM(95, "Moderate thunderstorm"),
    SLIGHT_THUNDERSTORM_WITH_HAIL(96, "Thunderstorm with slight hail"),
    HEAVY_THUNDERSTORM_WITH_HAIL(99, "Thunderstorm with heavy hail");

    private final int code;
    private final String description;

    WeatherCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static String getDescriptionByCode(int code) {
        for (WeatherCode weatherCode : values()) {
            if (weatherCode.code == code) {
                return weatherCode.description;
            }
        }
        return "Unknown weather code";
    }
}
