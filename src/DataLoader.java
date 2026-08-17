import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Reads the simulation's input files (cities, vehicles, packages and
 * missions) from {@value #DATA_DIR}. Paths are resolved relative to the
 * working directory, which IntelliJ sets to the module root by default.
 */
public class DataLoader {

    public static final String DATA_DIR = "src/txtfiles/";

    private static final String CITIES_FILE = "cities.txt";
    private static final String VEHICLES_FILE = "vehicles.txt";
    private static final String PACKAGES_FILE = "packages.txt";
    private static final String MISSIONS_FILE = "missons.txt";

    public Cities[] loadCities() throws IOException {
        List<String> lines = readLines(CITIES_FILE);
        Cities[] cities = new Cities[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            Cities city = new Cities();
            city.name = lines.get(i);
            cities[i] = city;
        }
        return cities;
    }

    public Vehicles[] loadVehicles() throws IOException {
        List<String> lines = readLines(VEHICLES_FILE);
        Vehicles[] vehicles = new Vehicles[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split("\\s+");
            Vehicles vehicle = new Vehicles();
            vehicle.setName(fields[0]);
            vehicle.setCity(fields[1]);
            vehicle.setCapacity(Double.parseDouble(fields[2]));
            vehicles[i] = vehicle;
        }
        return vehicles;
    }

    public Packages[] loadPackages() throws IOException {
        List<String> lines = readLines(PACKAGES_FILE);
        Packages[] packages = new Packages[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split("\\s+");
            Packages pack = new Packages();
            pack.setName(fields[0]);
            pack.setCity(fields[1]);
            packages[i] = pack;
        }
        return packages;
    }

    /**
     * Each line is one mission, its fields already separated by "-"
     * (origin-relay-destination-firstBatchSize-secondBatchSize-dropIndexes).
     * Lines are flattened into a single token stream so that a file with
     * several missions simply yields several consecutive 6-token groups.
     */
    public String[] loadMissions() throws IOException {
        List<String> lines = readLines(MISSIONS_FILE);
        List<String> tokens = new ArrayList<>();
        for (String line : lines) {
            tokens.addAll(Arrays.asList(line.split("-")));
        }
        return tokens.toArray(new String[0]);
    }

    private List<String> readLines(String fileName) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_DIR + fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }
}
