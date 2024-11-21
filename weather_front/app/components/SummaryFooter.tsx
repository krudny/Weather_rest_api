import { SummaryData } from "@/app/interfaces/SummaryData";
import React from "react";

export default function SummaryFooter({
  summary,
}: {
  summary: SummaryData | null;
}) {
  if (!summary) {
    return;
  }

  return (
    <div className="mt-12 p-6 bg-gray-800 text-white rounded-lg">
      <p className="text-2xl mb-7">Weekly summary</p>
      <div className="">
        {Object.entries(summary).map(([key, value]) => (
          <div key={key} className="flex justify-between">
            <span className="font-medium">{key}:</span>
            <span>{value}</span>
          </div>
        ))}
      </div>
    </div>
  );
}
