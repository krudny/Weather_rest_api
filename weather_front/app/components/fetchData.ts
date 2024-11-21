import { ForecastData } from "@/app/interfaces/ForecastData";
import { SummaryData } from "@/app/interfaces/SummaryData";

export async function fetchData(
  latitude: string,
  longitude: string,
  setWeeklyForecast: (data: ForecastData | null) => void,
  setWeeklySummary: (data: SummaryData | null) => void,
) {
  try {
    const forecastResponse = await fetch(
      `http://localhost:8080/weekly_forecast?latitude=${latitude}&longitude=${longitude}`,
    );
    const forecastData = await forecastResponse.json();
    setWeeklyForecast(forecastData);

    const summaryResponse = await fetch(
      `http://localhost:8080/weekly_summary?latitude=${latitude}&longitude=${longitude}`,
    );
    const summaryData = await summaryResponse.json();
    setWeeklySummary(summaryData);
  } catch (error) {
    console.error("Error fetching data:", error);
  }
}
