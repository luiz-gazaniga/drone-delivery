package usecase.multipleknapsack;

import org.drone.delivery.domain.Drone;
import org.drone.delivery.domain.InputData;
import org.drone.delivery.domain.Location;
import org.drone.delivery.input.InputFileParser;
import org.drone.delivery.output.OutputFileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MultipleKnapsackDroneDeliveryServiceRefactorTest {
    public static void main(String[] args) throws IOException {
        execute(args);
    }

    private static void execute(String[] args) throws IOException {
        InputData data = InputFileParser.parse(args);
        assignDeliveries(data.getDrones(), data.getLocations());
        printResults(args, data.getDrones());
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

    public static void printResults(String[] args, List<Drone> drones) {
        OutputFileWriter.writeResults(args, drones);
    }
}