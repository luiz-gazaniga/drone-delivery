package usecase.greedy3;

import usecase.multipleknapsack.MultipleKnapsackDroneDeliveryServiceTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GreedyDroneDeliveryService3Test {
    static class Drone {
        private String name;
        private int maxWeight;
        private List<List<Location>> deliveries;

        public Drone(String name, int maxWeight) {
            this.name = name;
            this.maxWeight = maxWeight;
            this.deliveries = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public int getMaxWeight() {
            return maxWeight;
        }

        public List<List<Location>> getDeliveries() {
            return deliveries;
        }

        public void addLocation(Location location) {
            deliveries.add(Collections.singletonList(location));
        }

        public void addDelivery(List<Location> locations) {
            deliveries.add(locations);
        }
    }

    static class Location {
        private String name;
        private int totalWeight;

        public Location(String name) {
            this.name = name;
            this.totalWeight = 0;
        }

        public String getName() {
            return name;
        }

        public int getTotalWeight() {
            return totalWeight;
        }

        public void addPackage(int weight) {
            totalWeight += weight;
        }
    }
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Please provide an input file.");
            return;
        }

        String inputFile = args[0];
        BufferedReader reader = new
                BufferedReader(new FileReader(inputFile));

        // Parse input data
        List<Drone> drones = new ArrayList<>();
        Map<String, Location> locations = new HashMap<>();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            String name = tokens[0].trim().replaceAll("[\\[\\](){}]", "");
            int maxWeight = Integer.parseInt(tokens[1].trim().replaceAll("[\\[\\](){}]", ""));
            drones.add(new Drone(name, maxWeight));
            for (int i = 2; i < tokens.length; i += 2) {
                String locationName = tokens[i].trim().replaceAll("[\\[\\](){}]", "");
                int weight = Integer.parseInt(tokens[i + 1].trim().replaceAll("[\\[\\](){}]", ""));
                Location location = locations.computeIfAbsent(locationName, k -> new Location(locationName));
                location.addPackage(weight);
                drones.get(drones.size() - 1).addLocation(location);
            }
        }
        reader.close();

        // Sort drones by descending order of maxWeight
        drones.sort((d1, d2) -> Integer.compare(d2.getMaxWeight(), d1.getMaxWeight()));

        // Assign packages to drones
        for (Drone drone : drones) {
            List<Location> locationsToDeliver = new ArrayList<>(locations.values());
            locationsToDeliver.sort((l1, l2) -> Integer.compare(l2.getTotalWeight(), l1.getTotalWeight()));
            while (!locationsToDeliver.isEmpty()) {
                Location bestFit = null;
                int remainingWeight = drone.getMaxWeight();
                List<Location> deliveries = new ArrayList<>();
                for (Location location : locationsToDeliver) {
                    if (location.getTotalWeight() <= remainingWeight) {
                        bestFit = location;
                        deliveries.add(location);
                        remainingWeight -= location.getTotalWeight();
                    }
                }
                if (bestFit == null) {
                    break; // No more deliveries possible for this drone
                }
                drone.addDelivery(deliveries);
                locationsToDeliver.removeAll(deliveries);
            }
        }

        // Print results
        for (Drone drone : drones) {
            System.out.println("[" + drone.getName() + "]");
            int tripNumber = 1;
            for (List<Location> deliveries : drone.getDeliveries()) {
                System.out.println("Trip #" + tripNumber++);
                for (Location location : deliveries) {
                    System.out.println("[" + location.getName() + "]");
                }
            }
        }
    }
}