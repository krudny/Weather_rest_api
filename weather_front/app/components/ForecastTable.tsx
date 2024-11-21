import {
  FaCloudRain,
  FaSnowflake,
  FaSun,
  FaCloud,
  FaCloudSun,
} from "react-icons/fa";
import React from "react";
import { ForecastData } from "@/app/interfaces/ForecastData";

const properties: string[] = [
  "Date",
  "Weather",
  "Min Temp",
  "Max Temp",
  "Energy Produced",
];

const getWeatherIcon = (code: number) => {
  if (code >= 0 && code <= 3) {
    return <FaSun />;
  } else if (code >= 4 && code <= 50) {
    return <FaCloud />;
  } else if (code >= 51 && code <= 70) {
    return <FaCloudRain />;
  } else if (code >= 71 && code <= 90) {
    return <FaSnowflake />;
  } else {
    return <FaCloudSun />;
  }
};

export default function ForecastTable({
  forecast,
}: {
  forecast: ForecastData | null;
}) {
  if (!forecast) {
    return;
  }

  return (
    <div className="max-w-full">
      <p className="text-2xl my-7">Weekly Forecast</p>
      <div className="max-w-full flex justify-center items-center">
        <table className="w-full border border-gray-700 ">
          <thead>
            <tr>
              {properties.map((property, index) => (
                <th key={index} className="td-styles">
                  {property}
                </th>
              ))}
            </tr>
          </thead>
          <tbody>
            {Object.entries(forecast).map(([date, dayData]) => (
              <tr key={date} className="text-center">
                <td className="td-styles ">{date}</td>
                <td className="td-styles">
                  <div className="flex justify-center">
                    {getWeatherIcon(dayData["Weather code"])}
                  </div>
                </td>
                <td className="td-styles">{dayData["Max temperature"]}°C</td>
                <td className="td-styles">{dayData["Min temperature"]}°C</td>
                <td className="td-styles">{dayData["Energy produced"]}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
