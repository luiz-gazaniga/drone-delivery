package usecase;

import org.drone.delivery.domain.Drone;
import org.drone.delivery.domain.InputData;
import org.drone.delivery.domain.Location;
import org.drone.delivery.input.InputFileParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class _03DroneDeliveryServiceUseInputParserTest {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Please provide an input data file.");
            System.exit(0);
        }

        String fileName = DroneDeliveryServiceTest.class.getClassLoader().getResource(args[0]).getFile();
        InputData inputData = InputFileParser.parse(fileName);
        List<Drone> drones = inputData.getDrones();
        List<Location> locations = inputData.getLocations();

        // Process deliveries
        for (Drone drone : drones) {
            int tripCounter = 1;
            while (!locations.isEmpty()) {
                List<Location> currentTrip = drone.planTrip(locations);
                if (currentTrip.isEmpty()) {
                    break;
                }
                System.out.println(drone.getName() + "\nTrip #" + tripCounter++);
                for (Location location : currentTrip) {
                    System.out.println(location.getName() + ", [" + location.getWeight() + "]");
                }
                locations.removeAll(currentTrip);
            }
        }
    }
}
