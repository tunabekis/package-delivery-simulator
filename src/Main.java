import java.io.IOException;

/**
 * Simulates a package delivery network: vehicles and packages start out
 * stationed in cities, and a series of missions moves them between cities
 * using custom stack, queue and doubly-linked-list data structures.
 */
public class Main {

    public static void main(String[] args) {
        try {
            DataLoader loader = new DataLoader();
            Cities[] cities = loader.loadCities();
            Vehicles[] vehicles = loader.loadVehicles();
            Packages[] packages = loader.loadPackages();
            String[] missionTokens = loader.loadMissions();

            stationVehicles(cities, vehicles);
            stackPackages(cities, packages);

            new MissionService(cities).runAll(missionTokens);

            new ResultWriter().write(cities);
        } catch (IOException e) {
            throw new RuntimeException("Failed to run the delivery simulation", e);
        }
    }

    private static void stationVehicles(Cities[] cities, Vehicles[] vehicles) {
        for (Vehicles vehicle : vehicles) {
            cityNamed(cities, vehicle.getCity()).getVehicleQueue().enqueue(vehicle);
        }
    }

    private static void stackPackages(Cities[] cities, Packages[] packages) {
        for (Packages pack : packages) {
            cityNamed(cities, pack.getCity()).getPackageStack().push(pack);
        }
    }

    private static Cities cityNamed(Cities[] cities, String name) {
        for (Cities city : cities) {
            if (city.name.equals(name)) {
                return city;
            }
        }
        throw new IllegalArgumentException("Unknown city: " + name);
    }
}
