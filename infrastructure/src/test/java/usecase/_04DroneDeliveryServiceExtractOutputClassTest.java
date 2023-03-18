package usecase;

import org.drone.delivery.domain.Drone;
import org.drone.delivery.domain.InputData;
import org.drone.delivery.domain.Location;
import org.drone.delivery.input.InputFileParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class _04DroneDeliveryServiceExtractOutputClassTest {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Please provide an input data file.");
            System.exit(0);
        }

        String fileName = _04DroneDeliveryServiceExtractOutputClassTest.class.getClassLoader().getResource(args[0]).getFile();
        InputData inputData = InputFileParser.parse(fileName);
        List<Drone> drones = inputData.getDrones();
        List<Location> locations = inputData.getLocations();

        List<String> messages = new ArrayList<>();

        // Process deliveries
        for (Drone drone : drones) {
            int tripCounter = 1;
            while (!locations.isEmpty()) {
                List<Location> currentTrip = drone.planTrip(locations);
                if (currentTrip.isEmpty()) {
                    break;
                }
                messages.add(drone.getName() + "\nTrip #" + tripCounter++);
                for (Location location : currentTrip) {
                    messages.add(location.getName() + ", [" + location.getWeight() + "]");
                }
                locations.removeAll(currentTrip);
            }
        }

        // Print output messages
        for (String message : messages) {
            System.out.println(message);
        }
    }
}
