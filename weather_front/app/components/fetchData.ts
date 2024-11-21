import { ForecastData } from "@/app/interfaces/ForecastData";
import { SummaryData } from "@/app/interfaces/SummaryData";

export async function fetchData(
  latitude: number,
  longitude: number,
  setWeeklyForecast: (data: ForecastData | null) => void,
  setWeeklySummary: (data: SummaryData | null) => void,
) {
  try {
    const forecastResponse = await fetch(
      `https://weather-backend-465897916721.us-central1.run.app/weekly_forecast?latitude=${latitude}&longitude=${longitude}`,
    );
    const forecastData = await forecastResponse.json();
    setWeeklyForecast(forecastData);

    const summaryResponse = await fetch(
      `https://weather-backend-465897916721.us-central1.run.app/weekly_summary?latitude=${latitude}&longitude=${longitude}`,
    );
    const summaryData = await summaryResponse.json();
    setWeeklySummary(summaryData);
  } catch (error) {
    console.error("Error fetching data:", error);
  }
}
