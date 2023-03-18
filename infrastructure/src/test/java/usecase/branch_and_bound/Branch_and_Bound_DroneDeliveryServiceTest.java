package usecase.branch_and_bound;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Drone {
    String name;
    int maxWeight;
    ArrayList<Location> deliveries;

    Drone(String name, int maxWeight) {
        this.name = name;
        this.maxWeight = maxWeight;
        deliveries = new ArrayList<>();
    }

    void addLocation(Location location) {
        deliveries.add(location);
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

public class Branch_and_Bound_DroneDeliveryServiceTest {
    static final String OUTPUT_FILE = "output.txt";

    public static void main(String[] args) {
        try {
            String inputFile = args[0];
            List<Drone> drones = new ArrayList<>();
            List<Location> locations = new ArrayList<>();
            readInputFile(inputFile, drones, locations);
            findOptimalTrips(drones, locations);
            writeOutputFile(inputFile, drones);
        } catch (IOException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
        }
    }

    static void readInputFile(String inputFile, List<Drone> drones, List<Location> locations) throws IOException {
        BufferedReader reader = new
                BufferedReader(new FileReader(inputFile));

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
    }

    static void findOptimalTrips(List<Drone> drones, List<Location> locations) {
        // Sort drones by maxWeight, decreasing order
        drones.sort((d1, d2) -> Integer.compare(d2.maxWeight, d1.maxWeight));

        // Sort locations by weight, decreasing order
        locations.sort((l1, l2) -> Integer.compare(l2.weight, l1.weight));

        for (Location location : locations) {
            Drone bestDrone = null;
            int maxRemainingWeight = -1;

            for (Drone drone : drones) {
                int remainingWeight = drone.maxWeight - location.weight;
                if (remainingWeight >= 0 && remainingWeight > maxRemainingWeight) {
                    bestDrone = drone;
                    maxRemainingWeight = remainingWeight;
                }
            }

            if (bestDrone != null) {
                bestDrone.addLocation(location);
            }
        }
    }

    static void writeOutputFile(String inputFile, List<Drone> drones) throws IOException {
        File file = new File(inputFile);
        String path = file.getParent();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + "\\" + OUTPUT_FILE))) {
            for (Drone drone : drones) {
                if (drone.deliveries.isEmpty()) {
                    continue;
                }

                writer.write(drone.name);
                writer.newLine();
                writer.write("Trip #1");
                writer.newLine();

                for (Location location : drone.deliveries) {
                    writer.write(location.name + ", " + location.weight);
                    writer.newLine();
                }
            }
        }
    }
}