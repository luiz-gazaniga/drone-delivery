package usecase.greedy4.greedy2;

import usecase.multipleknapsack.MultipleKnapsackDroneDeliveryServiceTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

class Drone {
    String name;
    int maxWeight;

    Drone(String name, int maxWeight) {
        this.name = name;
        this.maxWeight = maxWeight;
    }
}

class Location {
    String name;
    int weight;

    Location(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }
}

class Delivery {
    Drone drone;
    int tripNumber;
    List<Location> locations;
    int currentWeight;

    Delivery(Drone drone, int tripNumber) {
        this.drone = drone;
        this.tripNumber = tripNumber;
        this.locations = new ArrayList<>();
        this.currentWeight = 0;
    }

    void addLocation(Location location) {
        this.locations.add(location);
        this.currentWeight += location.weight;
    }

    boolean canAddLocation(Location location) {
        return this.currentWeight + location.weight <= this.drone.maxWeight;
    }
}

public class GreedyDroneDeliveryService4Test {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Please provide an input file.");
            return;
        }

        String fileName = GreedyDroneDeliveryService4Test.class.getClassLoader().getResource(args[0]).getFile();
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

        // Sort drones and locations
        drones.sort(Comparator.comparing(d -> d.name));
        locations.sort((a, b) -> {
            int weightComparison = b.weight - a.weight;
            if (weightComparison != 0) {
                return weightComparison;
            } else {
                return b.name.compareTo(a.name);
            }
        });

        // Process deliveries
        List<Delivery> deliveries = new ArrayList<>();
        for (Drone drone : drones) {
            int tripNumber = 1;
            Delivery delivery = new Delivery(drone, tripNumber);
            Iterator<Location> iterator = locations.iterator();
            while (iterator.hasNext()) {
                Location location = iterator.next();
                if (delivery.canAddLocation(location)) {
                    delivery.addLocation(location);
                    iterator.remove();
                } else {
                    deliveries.add(delivery);
                    tripNumber++;
                    delivery = new Delivery(drone, tripNumber);
                    delivery.addLocation(location);
                    iterator.remove();
                }
            }
            if (!delivery.locations.isEmpty()) {
                deliveries.add(delivery);
            }
        }

        // Output deliveries
        for (Delivery delivery : deliveries) {
            System.out.println("[" + delivery.drone.name + "]");
            System.out.println("Trip #" + delivery.tripNumber);
            delivery.locations.sort(Comparator.comparing(l -> l.name));
            for (Location location : delivery.locations) {
                System.out.print("[" + location.name + "], ");
            }
            System.out.println();
        }
    }
}