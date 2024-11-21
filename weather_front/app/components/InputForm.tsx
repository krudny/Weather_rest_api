"use client";

import { useState } from "react";
import { ForecastData } from "@/app/interfaces/ForecastData";
import { SummaryData } from "@/app/interfaces/SummaryData";
import { fetchData } from "@/app/components/fetchData"; // Assuming this path

export default function InputForm({
  setWeeklyForecast,
  setWeeklySummary,
}: {
  setWeeklyForecast: (data: ForecastData | null) => void;
  setWeeklySummary: (data: SummaryData | null) => void;
}) {
  const [latitude, setLatitude] = useState("");
  const [longitude, setLongitude] = useState("");

  const handleFetchData = () => {
    if (!latitude || !longitude) {
      alert("Please enter both latitude and longitude.");
      return;
    }

    fetchData(latitude, longitude, setWeeklyForecast, setWeeklySummary).catch(
      (error) => {
        console.error("Error fetching data:", error);
        alert("Failed to fetch data, please try again.");
      },
    );
  };

  return (
      <div className="mx-auto flex justify-center gap-2 w-full mt-10 flex-col sm:flex-row">
          <input
              type="number"
              value={latitude}
              onChange={(e) => setLatitude(e.target.value)}
              placeholder="Enter latitude"
              className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <input
              type="number"
              value={longitude}
              onChange={(e) => setLongitude(e.target.value)}
              placeholder="Enter longitude"
              className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <button
              onClick={handleFetchData}
              className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
          >
              Get Forecast
          </button>
      </div>
  );
}
