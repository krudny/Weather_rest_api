"use client";

import ForecastTable from "@/app/components/ForecastTable";
import SummaryFooter from "@/app/components/SummaryFooter";
import InputForm from "@/app/components/InputForm";
import { ForecastData } from "@/app/interfaces/ForecastData";
import { useState } from "react";
import { SummaryData } from "@/app/interfaces/SummaryData";

export default function Home() {
  const [weeklyForecast, setWeeklyForecast] = useState<ForecastData | null>(null);
  const [weeklySummary, setWeeklySummary] = useState<SummaryData | null>(null);

  return (
      <div className="container mx-auto p-3">
          <div className="w-full flex justify-center my-2">
              <h1 className="text-lg">Weekly Forecast Service</h1>
          </div>

          <InputForm
              setWeeklyForecast={setWeeklyForecast}
              setWeeklySummary={setWeeklySummary}
          />
          <ForecastTable forecast={weeklyForecast}/>
          <SummaryFooter summary={weeklySummary}/>
      </div>
  );
}
