package usecase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DroneDeliveryServiceTestReadingDataFromTextFile {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Please provide an input data file.");
            System.exit(0);
        }

        String fileName = DroneDeliveryServiceTest.class.getClassLoader().getResource(args[0]).getFile();
        BufferedReader reader = new
                BufferedReader(new FileReader(fileName));

        List<Drone> drones = new ArrayList<>();
        ArrayList<Location> locations = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(", ");
            for (int i = 0; i < tokens.length; i += 2) {
                String itemType = tokens[i].substring(1);
                int itemValue = Integer.parseInt(tokens[i + 1].substring(1, tokens[i + 1].length() - 1));
                if (itemType.startsWith("Drone")) {
                    drones.add(new Drone(itemType, itemValue));
                } else if (itemType.startsWith("Location")) {
                    locations.add(new Location(itemType, itemValue));
                }
            }
        }
        reader.close();

        // Process deliveries
        for (Drone drone : drones) {
            int tripCounter = 1;
            while (!locations.isEmpty()) {
                ArrayList<Location> currentTrip = drone.planTrip(locations);
                if (currentTrip.isEmpty()) {
                    break;
                }
                System.out.println(drone.name + "\nTrip #" + tripCounter++);
                for (Location location : currentTrip) {
                    System.out.println(location.name + ", [" + location.weight + "]");
                }
                locations.removeAll(currentTrip);
            }
        }
    }

    static class Drone {
        String name;
        int maxWeight;

        Drone(String name, int maxWeight) {
            this.name = name;
            this.maxWeight = maxWeight;
        }

        ArrayList<Location> planTrip(ArrayList<Location> locations) {
            int[] dp = new int[maxWeight + 1];
            int n = locations.size();

            for (int i = 0; i < n; i++) {
                for (int j = maxWeight; j >= locations.get(i).weight; j--) {
                    dp[j] = Math.max(dp[j], dp[j - locations.get(i).weight] + locations.get(i).weight);
                }
            }

            ArrayList<Location> selectedLocations = new ArrayList<>();
            int remainingCapacity = maxWeight;

            for (int i = n - 1; i >= 0; i--) {
                if (remainingCapacity >= locations.get(i).weight &&
                        dp[remainingCapacity] - dp[remainingCapacity - locations.get(i).weight] == locations.get(i).weight) {
                    selectedLocations.add(locations.get(i));
                    remainingCapacity -= locations.get(i).weight;
                }
            }

            return selectedLocations;
        }
    }

    static class Location {
        String name;
        int weight;

        Location(String name, int weight) {
            this.name = name;
            this.weight = weight;
        }
    }
}
