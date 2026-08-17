import java.util.Arrays;

/**
 * Executes delivery missions read from the missions file. Each mission
 * moves a vehicle from an origin city through a relay city to a
 * destination city, picking up packages along the way and dropping two of
 * them off at the relay city.
 * <p>
 * Each mission is described by exactly 6 consecutive tokens:
 * {@code origin, relay, destination, firstBatchSize, secondBatchSize, dropIndexes}.
 */
public class MissionService {

    private static final int FIELDS_PER_MISSION = 6;

    private final Cities[] cities;

    public MissionService(Cities[] cities) {
        this.cities = cities;
    }

    /** Runs every mission encoded in {@code missionTokens}, in order. */
    public void runAll(String[] missionTokens) {
        for (int offset = 0; offset < missionTokens.length; offset += FIELDS_PER_MISSION) {
            String[] mission = Arrays.copyOfRange(missionTokens, offset, offset + FIELDS_PER_MISSION);
            runMission(mission);
        }
    }

    private void runMission(String[] mission) {
        Cities originCity = findCityByName(mission[0]);
        Cities relayCity = findCityByName(mission[1]);
        Cities destinationCity = findCityByName(mission[2]);
        int firstBatchSize = Integer.parseInt(mission[3]);
        int secondBatchSize = Integer.parseInt(mission[4]);
        int[] dropIndexes = parseDropIndexes(mission[5]);

        DoublyLinkedList<Transportable> convoy = new DoublyLinkedList<>();

        // Load the first batch from the origin city, then place the vehicle
        // at the head of the convoy so it leads the packages it carries.
        for (int i = 0; i < firstBatchSize; i++) {
            convoy.addLast(originCity.getPackageStack().pop());
        }
        convoy.addFirst(originCity.getVehicleQueue().dequeue());

        // Pick up a second batch at the relay city; these are provisionally
        // bound for the destination city.
        for (int i = 0; i < secondBatchSize; i++) {
            Packages parcel = relayCity.getPackageStack().pop();
            parcel.setCity(destinationCity.name);
            convoy.addLast(parcel);
        }

        // Drop two packages off at the relay city. The second index is
        // shifted back by one because removing the first drop item already
        // shifted every later position left by one.
        Transportable dropped1 = convoy.removeAfter(dropIndexes[0]);
        Transportable dropped2 = convoy.removeAfter(dropIndexes[1] - 1);
        dropped1.setCity(relayCity.name);
        dropped2.setCity(relayCity.name);
        relayCity.getPackageStack().push((Packages) dropped1);
        relayCity.getPackageStack().push((Packages) dropped2);

        // Everything else in the convoy continues on to the destination
        // city. Packages are unloaded from the tail (reversing their order)
        // through a temporary stack so they land at the destination in
        // their original relative order.
        int remainingPackages = firstBatchSize + secondBatchSize - dropIndexes.length;
        MyStack<Packages> reorderBuffer = new MyStack<>();
        for (int i = 0; i < remainingPackages; i++) {
            Packages parcel = (Packages) convoy.deleteLast();
            parcel.setCity(destinationCity.name);
            reorderBuffer.push(parcel);
        }
        for (int i = 0; i < remainingPackages; i++) {
            destinationCity.getPackageStack().push(reorderBuffer.pop());
        }

        // The vehicle itself is now stationed at the destination city.
        Vehicles vehicle = (Vehicles) convoy.deleteFirst();
        destinationCity.getVehicleQueue().enqueue(vehicle);
    }

    private Cities findCityByName(String name) {
        for (Cities city : cities) {
            if (city.name.equals(name)) {
                return city;
            }
        }
        throw new IllegalArgumentException("Unknown city: " + name);
    }

    private int[] parseDropIndexes(String field) {
        String[] parts = field.split(",");
        int[] indexes = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            indexes[i] = Integer.parseInt(parts[i]);
        }
        return indexes;
    }
}
