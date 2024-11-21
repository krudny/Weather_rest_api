"use client";

import ForecastTable from "@/app/components/ForecastTable";
import SummaryFooter from "@/app/components/SummaryFooter";
import InputForm from "@/app/components/InputForm";
import { ForecastData } from "@/app/interfaces/ForecastData";
import { useEffect, useState } from "react";
import { SummaryData } from "@/app/interfaces/SummaryData";
import { fetchData } from "@/app/components/fetchData";
import setCurrentLocation from "@/app/components/localize";

export default function Home() {
  const [weeklyForecast, setWeeklyForecast] = useState<ForecastData | null>(
    null,
  );
  const [weeklySummary, setWeeklySummary] = useState<SummaryData | null>(null);
  const [latitude, setLatitude] = useState<number | null>(null);
  const [longitude, setLongitude] = useState<number | null>(null);

  useEffect(() => {
    const fetchLocationData = async () => {
      try {
        setCurrentLocation(setLatitude, setLongitude);
      } catch (error) {
        console.error("Error getting location:", error);
      }
    };

    fetchLocationData();
  }, []);

  useEffect(() => {
    if (latitude && longitude) {
      fetchData(latitude, longitude, setWeeklyForecast, setWeeklySummary).catch(
        (error) => {
          console.error("Error fetching forecast data:", error);
        },
      );
    }
  }, [latitude, longitude]);

  return (
    <div className="container mx-auto p-3">
      <div className="w-full flex flex-col items-center justify-between my-2">
        <h1 className="text-lg">Weekly Forecast Service</h1>
        <p>
          Your coords: ({latitude ?? "Fetching..."},{longitude ?? "Fetching..."}
          )
        </p>
      </div>

      <InputForm
        setLatitude={setLatitude}
        setLongitude={setLongitude}
        setWeeklyForecast={setWeeklyForecast}
        setWeeklySummary={setWeeklySummary}
      />
      <ForecastTable forecast={weeklyForecast} />
      <SummaryFooter summary={weeklySummary} />
    </div>
  );
}
