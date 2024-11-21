import {FaCloudRain, FaSnowflake, FaSun, FaCloud, FaCloudSun} from "react-icons/fa";
import React from "react";
import {ForecastData} from "@/app/interfaces/ForecastData";

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

export default async function ForecastTable() {
  const data2 = await fetch(
    "http://localhost:8080/weekly_forecast?latitude=52.52&longitude=23.52",
  );
  const fetched_data:ForecastData = await data2.json();

  return (
    <div>
      <p className="text-2xl my-7">Weekly forecast</p>
      <div className="w-100 flex justify-center items-center">
        <table className="min-w-full border border-gray-700">
          <thead>
            <tr>
              {properties.map((property, index) => (
                <th key={index} className="border border-gray-700">
                  {property}
                </th>
              ))}
            </tr>
          </thead>
          <tbody>
            {Object.entries(fetched_data).map(([date, dayData]) => (
              <tr key={date} className="w-full items-center text-center">
                <td className="border border-gray-700 p-2">{date}</td>
                <td className="border border-gray-700 p-2">
                  <div className="w-full h-full flex justify-center">
                    {getWeatherIcon(dayData["Weather code"])}
                  </div>

                </td>
                <td className="border border-gray-700 p-2">
                  {dayData["Max temperature"]}
                </td>
                <td className="border border-gray-700 p-2">
                  {dayData["Min temperature"]}
                </td>
                <td className="border border-gray-700 p-2">
                  {dayData["Energy produced"]}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
