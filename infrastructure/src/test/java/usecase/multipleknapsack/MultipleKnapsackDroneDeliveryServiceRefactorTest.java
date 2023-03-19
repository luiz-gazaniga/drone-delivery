package usecase.multipleknapsack;

import org.drone.delivery.domain.Drone;
import org.drone.delivery.domain.InputData;
import org.drone.delivery.domain.Location;
import org.drone.delivery.input.InputFileParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MultipleKnapsackDroneDeliveryServiceRefactorTest {
    public static void main(String[] args) throws IOException {
        if (validateArgs(args)) return;

        InputData data = InputFileParser.parse(args[0]);
        assignDeliveries(data.getDrones(), data.getLocations());

        printResults(data.getDrones());

    }

    private static boolean validateArgs(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide an input file.");
            return true;
        }
        return false;
    }

    public static void assignDeliveries(List<Drone> drones, List<Location> locations) {
        locations.sort(Comparator.comparingInt((Location l) -> l.getWeight()));
        drones.sort(Comparator.comparingInt((Drone d) -> d.getMaxWeight()).reversed());

        while (!locations.isEmpty()) {
            for (Drone drone : drones) {
                List<Location> currentTrip = new ArrayList<>();
                int currentWeight = 0;
                for (Location location : new ArrayList<>(locations)) {
                    if (currentWeight + location.getWeight() <= drone.getMaxWeight()) {
                        currentWeight += location.getWeight();
                        currentTrip.add(location);
                        locations.remove(location);
                    }
                }
                if (!currentTrip.isEmpty()) {
                    drone.getTrips().add(currentTrip);
                }
            }
        }
    }

    public static void printResults(List<Drone> drones) {
        for (Drone drone : drones) {
            System.out.println("[" + drone.getName() + "]");
            for (int i = 0; i < drone.getTrips().size(); i++) {
                System.out.println("Trip #" + (i + 1));
                drone.getTrips().get(i).forEach(location -> System.out.println("[" + location.getName() + "]"));
            }
            System.out.println();
        }
    }
}