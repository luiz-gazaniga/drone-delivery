package research.branch_and_bound;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
    static final String OUTPUT_FILE = "/output_%s.txt";

    public static void main(String[] args) {
        try {
            String inputFile = args[0];
            List<Drone> drones = new ArrayList<>();
            List<Location> locations = new ArrayList<>();
            String outputDirectory = extractOutputDirectory(inputFile);
            readInputFile(inputFile, drones, locations);
            findOptimalTrips(drones, locations);
            writeOutputFile(outputDirectory, drones);
        } catch (IOException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
        }
    }

    private static String extractOutputDirectory(String inputFile) {
        File file = new File(inputFile);
        return file.getParent();
    }

    static void readInputFile(String inputFile, List<Drone> drones, List<Location> locations) throws IOException {
        BufferedReader reader = new
                BufferedReader(new FileReader(inputFile));

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
    }

    static void findOptimalTrips(List<Drone> drones, List<Location> locations) {
        // Sort drones by maxWeight, decreasing order
        drones.sort((d1, d2) -> Integer.compare(d2.maxWeight, d1.maxWeight));

        // Sort locations by weight, decreasing order
        locations.sort((l1, l2) -> Integer.compare(l2.weight, l1.weight));

        int droneIndex = 0;

        for (Location location : locations) {
            Drone bestDrone = null;
            int maxRemainingWeight = -1;

            // Look for the first drone that can carry the location
            for (int i = droneIndex; i < drones.size(); i++) {
                Drone drone = drones.get(i);
                int remainingWeight = drone.maxWeight - location.weight;
                if (remainingWeight >= 0 && remainingWeight > maxRemainingWeight) {
                    bestDrone = drone;
                    maxRemainingWeight = remainingWeight;
                    droneIndex = i;
                }
            }

            if (bestDrone != null) {
                bestDrone.addLocation(location);
            }
        }

        // Sort the deliveries of each drone by name
        for (Drone drone : drones) {
            drone.deliveries.sort((l1, l2) -> l1.name.compareTo(l2.name));
        }
    }

    static void writeOutputFile(String outputDirectory, List<Drone> drones) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        String fileName = outputDirectory + String.format(OUTPUT_FILE, timeStamp);
        System.out.println("Writing file: " + fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Drone drone : drones) {
                if (drone.deliveries.isEmpty()) {
                    continue;
                }

                writer.write(String.format("[%s]", drone.name));
                System.out.println(String.format("[%s]", drone.name));
                writer.newLine();

                List<List<Location>> trips = divideIntoTrips(drone.deliveries, drone.maxWeight);
                for (int i = 0; i < trips.size(); i++) {
                    writer.write(String.format("Trip #%d", i + 1));
                    System.out.println(String.format("Trip #%d", i + 1));
                    writer.newLine();

                    StringBuilder sb = new StringBuilder();
                    for (Location location : trips.get(i)) {
                        sb.append(String.format("[%s], ", location.name));
                    }
                    writer.write(sb.toString().replaceAll(", $", ""));
                    System.out.println(sb.toString().replaceAll(", $", ""));
                    writer.newLine();
                }
            }
        }
        System.out.println("Process complete");
    }

    static List<List<Location>> divideIntoTrips(List<Location> deliveries, int maxWeight) {
        List<List<Location>> trips = new ArrayList<>();
        List<Location> currentTrip = new ArrayList<>();
        int currentWeight = 0;

        for (Location location : deliveries) {
            if (currentWeight + location.weight <= maxWeight) {
                currentTrip.add(location);
                currentWeight += location.weight;
            } else {
                trips.add(currentTrip);
                currentTrip = new ArrayList<>();
                currentTrip.add(location);
                currentWeight = location.weight;
            }
        }

        if (!currentTrip.isEmpty()) {
            trips.add(currentTrip);
        }

        return trips;
    }
}