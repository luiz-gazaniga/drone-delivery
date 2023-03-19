package usecase.greedy;

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

public class GreedyDroneDeliveryService1Test {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Please provide an input file.");
            return;
        }

        String fileName = GreedyDroneDeliveryService1Test.class.getClassLoader().getResource(args[0]).getFile();
        BufferedReader reader = new
                BufferedReader(new FileReader(fileName));

        List<Drone> drones = new ArrayList<>();
        ArrayList<Location> locations = new ArrayList<>();

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

        // Sort drones and locations in descending order
        drones.sort(Comparator.comparing(d -> d.name));
        //locations.sort((a, b) -> b.weight - a.weight);
        locations.sort((a, b) -> {
            int weightComparison = b.weight - a.weight;
            if (weightComparison != 0) {
                return weightComparison;
            } else {
                return b.name.compareTo(a.name);
            }
        });

        // Process deliveries
        int tripNumber;
        while (!locations.isEmpty()) {
            tripNumber = 1;
            for (Drone drone : drones) {
                int currentWeight = 0;
                List<Location> currentTrip = new ArrayList<>();
                Iterator<Location> iterator = locations.iterator();
                while (iterator.hasNext()) {
                    Location location = iterator.next();
                    if (currentWeight + location.weight <= drone.maxWeight) {
                        currentWeight += location.weight;
                        currentTrip.add(location);
                        iterator.remove();
                    }
                }

                if (!currentTrip.isEmpty()) {
                    System.out.println("[" + drone.name + "]");
                    System.out.println("Trip #" + tripNumber);
                    for (Location location : currentTrip) {
                        System.out.println("[" + location.name + "]");
                    }
                }
                tripNumber++;
            }
        }
    }
}