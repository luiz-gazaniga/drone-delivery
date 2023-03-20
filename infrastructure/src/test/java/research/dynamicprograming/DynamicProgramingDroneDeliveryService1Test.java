package research.dynamicprograming;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DynamicProgramingDroneDeliveryService1Test {
    private static class Drone {
        String name;
        int capacity;

        public Drone(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
        }
    }

    private static class Location {
        String name;
        int weight;

        public Location(String name, int weight) {
            this.name = name;
            this.weight = weight;
        }
    }
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Please provide an input file.");
            return;
        }

        String fileName = DynamicProgramingDroneDeliveryService1Test.class.getClassLoader().getResource(args[0]).getFile();
        BufferedReader reader = new
                BufferedReader(new FileReader(fileName));

        List<Drone> drones = new ArrayList<>();
        List<Location> locations = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(", ");
            for (int i = 0; i < tokens.length; i += 2) {
                String itemType = tokens[i].substring(1).replaceAll("[\\[\\](){}]", "");
                int itemValue = Integer.parseInt(tokens[i + 1].substring(1, tokens[i + 1].length() - 1).replaceAll("[\\[\\](){}]", ""));
                if (itemType.startsWith("Drone")) {
                    drones.add(new Drone(itemType, itemValue));
                } else if (itemType.startsWith("Location")) {
                    locations.add(new Location(itemType, itemValue));
                }
            }
        }
        reader.close();

        // Calculate results
        List<List<Location>>[] droneTrips = new List[drones.size()];
        for (int i = 0; i < drones.size(); i++) {
            droneTrips[i] = new ArrayList<>();
        }
        findOptimalTrips(drones, locations, 0, droneTrips);

        // Print results
        for (int i = 0; i < drones.size(); i++) {
            printTrips(drones.get(i), droneTrips[i]);
        }
    }

    private static boolean findOptimalTrips(List<Drone> drones, List<Location> locations, int locationIndex, List<List<Location>>[] droneTrips) {
        if (locationIndex == locations.size()) {
            return true;
        }

        Location currentLocation = locations.get(locationIndex);
        for (int i = 0; i < drones.size(); i++) {
            Drone drone = drones.get(i);
            boolean addedToExistingTrip = false;

            for (List<Location> trip : droneTrips[i]) {
                int totalTripWeight = trip.stream().mapToInt(location -> location.weight).sum();
                if (totalTripWeight + currentLocation.weight <= drone.capacity) {
                    trip.add(currentLocation);
                    addedToExistingTrip = true;
                    if (findOptimalTrips(drones, locations, locationIndex + 1, droneTrips)) {
                        return true;
                    }
                    trip.remove(trip.size() - 1);
                    break;
                }
            }

            if (!addedToExistingTrip) {
                List<Location> newTrip = new ArrayList<>();
                newTrip.add(currentLocation);
                droneTrips[i].add(newTrip);
                if (findOptimalTrips(drones, locations, locationIndex + 1, droneTrips)) {
                    return true;
                }
                droneTrips[i].remove(droneTrips[i].size() - 1);
            }
        }

        return false;
    }

    private static void printTrips(Drone drone, List<List<Location>> trips) {
        System.out.println("[" + drone.name + "]");
        int tripNumber = 1;

        for (List<Location> trip : trips) {
            System.out.println("Trip #" + tripNumber);
            tripNumber++;

            for (Location location : trip) {
                System.out.println("[" + location.name + "]");
            }
        }
    }
}