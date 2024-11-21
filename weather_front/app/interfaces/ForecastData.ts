export interface DayData {
    "Weather code": number;
    "Weather description": string;
    "Min temperature": number;
    "Max temperature": number;
    "Energy produced": string;
}

export interface ForecastData {
    [date: string]: DayData;
}