package usecase.multipleknapsack;

import usecase.dynamicprograming.DynamicProgramingDroneDeliveryService1Test;

import java.io.*;
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

public class MultipleKnapsackDroneDeliveryServiceTest {
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
        locations.sort(Comparator.comparingInt((Location l) -> l.weight));
        //drones.sort(Comparator.comparingInt((Drone d) -> d.maxWeight).reversed());

        while (!locations.isEmpty()) {
            for (Drone drone : drones) {
                List<Location> currentTrip = new ArrayList<>();
                int currentWeight = 0;
                for (Location location : new ArrayList<>(locations)) {
                    if (currentWeight + location.weight <= drone.maxWeight) {
                        currentWeight += location.weight;
                        currentTrip.add(location);
                        locations.remove(location);
                    }
                }
                if (!currentTrip.isEmpty()) {
                    drone.trips.add(currentTrip);
                }
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