"use client";

import { useState } from "react";
import { ForecastData } from "@/app/interfaces/ForecastData";
import { SummaryData } from "@/app/interfaces/SummaryData";
import { fetchData } from "@/app/components/fetchData";
import setCurrentLocation from "@/app/components/localize"; // Assuming this path

export default function InputForm({
  setLatitude,
  setLongitude,
  setWeeklyForecast,
  setWeeklySummary,
}: {
  setLatitude: React.Dispatch<React.SetStateAction<number | null>>;
  setLongitude: React.Dispatch<React.SetStateAction<number | null>>;
  setWeeklyForecast: (data: ForecastData | null) => void;
  setWeeklySummary: (data: SummaryData | null) => void;
}) {
  const [latitudeInput, setLatitudeInput] = useState<number | null>(null);
  const [longitudeInput, setLongitudeInput] = useState<number | null>(null);

  const handleFetchData = () => {
    if (latitudeInput === null || longitudeInput === null) {
      alert("Please enter both latitude and longitude.");
      return;
    }

    setLatitude(latitudeInput);
    setLongitude(longitudeInput);

    fetchData(
      latitudeInput,
      longitudeInput,
      setWeeklyForecast,
      setWeeklySummary,
    ).catch((error) => {
      console.error("Error fetching data:", error);
      alert("Failed to fetch data, please try again.");
    });
  };

  const resetToDefault = () => {
    try {
      setCurrentLocation(setLatitude, setLongitude);
    } catch (error) {
      console.error("Error getting location:", error);
    }
  };

  return (
    <div className="mx-auto flex justify-center gap-2 w-full mt-10 flex-col sm:flex-row">
      <input
        type="number"
        value={latitudeInput ?? ""}
        onChange={(e) => setLatitudeInput(parseInt(e.target.value, 10) || null)}
        placeholder="Enter latitude"
        className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
      />
      <input
        type="number"
        value={longitudeInput ?? ""}
        onChange={(e) =>
          setLongitudeInput(parseInt(e.target.value, 10) || null)
        }
        placeholder="Enter longitude"
        className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
      />
      <button
        onClick={handleFetchData}
        className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
      >
        Get Forecast
      </button>
      <button
        onClick={resetToDefault}
        className="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600"
      >
        Reset Location
      </button>
    </div>
  );
}
