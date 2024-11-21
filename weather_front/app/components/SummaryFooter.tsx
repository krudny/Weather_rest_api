export default async function SummaryFooter() {
  const data = await fetch(
    "http://localhost:8080/weekly_summary?latitude=52.52&longitude=23.52",
  );
  const fetched_data = await data.json();

  console.log(fetched_data);
  return (
    <div className="mt-12 p-6 bg-gray-800 text-white rounded-lg">
      <p className="text-2xl mb-7">Weekly summary</p>
      <div className="">
        {Object.entries(fetched_data).map(([key, value]) => (
          <div key={key} className="flex justify-between">
            <span className="font-medium">{key}:</span>
            <span>{value}</span>
          </div>
        ))}
      </div>
    </div>
  );
}
