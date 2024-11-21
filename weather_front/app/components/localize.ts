export default function setCurrentLocation(
  setLatitude: React.Dispatch<React.SetStateAction<number | null>>,
  setLongitude: React.Dispatch<React.SetStateAction<number | null>>,
): void {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const { latitude, longitude } = position.coords;
        setLatitude(Math.round(latitude * 100) / 100);
        setLongitude(Math.round(longitude * 100) / 100);
      },
      (error) => {
        console.error("Error getting location:", error.message);
      },
    );
  } else {
    console.error("Geolocation is not supported by this browser.");
  }
}
