package usecase.multipleknapsack2;

import usecase.dynamicprograming.DynamicProgramingDroneDeliveryService1Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//Best until now

class Drone {
    String name;
    int maxWeight;
    List<List<Location>> trips;

    public Drone(String name, int maxWeight) {
        this.name = name;
        this.maxWeight = maxWeight;
        this.trips = new ArrayList<>();
    }
}

class Location {
    String name;
    int weight;

    public Location(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }
}

public class MultipleKnapsackDroneDeliveryService2Test {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Please provide an input file.");
            return;
        }

        String fileName = MultipleKnapsackDroneDeliveryService2Test.class.getClassLoader().getResource(args[0]).getFile();
        BufferedReader reader = new
                BufferedReader(new FileReader(fileName));

        List<Drone> drones = new ArrayList<>();
        List<Location> locations = new ArrayList<>();

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

        assignDeliveries(drones, locations);

        printResults(drones);

    }

    public static void assignDeliveries(List<Drone> drones, List<Location> locations) {
        locations.sort(Comparator.comparingInt((Location l) -> l.weight).reversed());
        drones.sort(Comparator.comparingInt((Drone d) -> d.maxWeight).reversed());

        while (!locations.isEmpty()) {
            boolean locationAssigned = false;

            for (Drone drone : drones) {
                List<Location> currentTrip = new ArrayList<>();
                int currentWeight = 0;
                Location bestLocation = null;
                int bestRemainingCapacity = drone.maxWeight;

                for (Location location : locations) {
                    int remainingCapacity = drone.maxWeight - currentWeight - location.weight;

                    if (remainingCapacity >= 0 && remainingCapacity < bestRemainingCapacity) {
                        bestLocation = location;
                        bestRemainingCapacity = remainingCapacity;
                    }
                }

                if (bestLocation != null) {
                    currentTrip.add(bestLocation);
                    locations.remove(bestLocation);
                    drone.trips.add(currentTrip);
                    locationAssigned = true;
                }
            }

            if (!locationAssigned) {
                break;
            }
        }
    }

    public static void printResults(List<Drone> drones) {
        for (Drone drone : drones) {
            System.out.println("[" + drone.name + "]");
            for (int i = 0; i < drone.trips.size(); i++) {
                System.out.println("Trip #" + (i + 1));
                drone.trips.get(i).forEach(location -> System.out.println("[" + location.name + "], " + "[" + location.weight + "]"));
            }
            System.out.println();
        }
    }
}