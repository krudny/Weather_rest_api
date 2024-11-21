import ForecastTable from "@/app/components/ForecastTable";
import SummaryFooter from "@/app/components/SummaryFooter";

export default async function Home() {
  return (
    <div className="container mx-auto p-3">
      <ForecastTable />
      <SummaryFooter />
    </div>
  );
}
