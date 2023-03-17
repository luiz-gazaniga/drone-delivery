package usecase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//class Drone {
//    String name;
//    int capacity;
//
//    Drone(String name, int capacity) {
//        this.name = name;
//        this.capacity = capacity;
//    }
//}
//
//class Location {
//    String name;
//    int weight;
//    boolean taken;
//
//    Location(String name, int weight) {
//        this.name = name;
//        this.weight = weight;
//        this.taken = false;
//    }
//}
//public class DroneDeliveryServiceTest {
//    static List<Location> bestDelivery;
//
//    public static void main(String[] args) {
//        List<Drone> drones = new ArrayList<>();
//        List<Location> locations = new ArrayList<>();
//
//        // Stub input data
//        String inputData = "[DroneA] 300 [DroneB] 350 [DroneC] 200 " +
//                "[LocationA] 200 [LocationB] 150 [LocationC] 50 " +
//                "[LocationD] 150 [LocationE] 100 [LocationF] 200 " +
//                "[LocationG] 50 [LocationH] 80 [LocationI] 70 [LocationJ] 50";
//
//        Scanner scanner = new Scanner(inputData);
//
//        while (scanner.hasNext()) {
//            String itemType = scanner.next();
//
//            if (itemType.startsWith("[Drone")) {
//                drones.add(new Drone(itemType, scanner.nextInt()));
//            } else if (itemType.startsWith("[Location")) {
//                locations.add(new Location(itemType, scanner.nextInt()));
//            }
//        }
//        scanner.close();
//
//        drones.sort(Comparator.comparing(d -> -d.capacity));
//        locations.sort(Comparator.comparing(l -> -l.weight));
//
//        int tripCount = 0;
//
//        while (!locations.isEmpty()) {
//            tripCount++;
//            System.out.print("Trip #" + tripCount + "\n");
//
//            for (Drone drone : drones) {
//                bestDelivery = new ArrayList<>();
//                findBestDelivery(new ArrayList<>(), locations, drone.capacity, 0);
//
//                if (!bestDelivery.isEmpty()) {
//                    System.out.println("[" + drone.name + "]");
//                    for (Location location : bestDelivery) {
//                        System.out.print("[" + location.name + "], ");
//                        locations.remove(location);
//                    }
//                    System.out.print("\n");
//                }
//            }
//        }
//    }
//
//    private static void findBestDelivery(List<Location> current, List<Location> locations, int capacity, int startIndex) {
//        int currentWeight = current.stream().mapToInt(l -> l.weight).sum();
//        int remainingCapacity = capacity - currentWeight;
//
//        if (remainingCapacity == 0) {
//            bestDelivery = current;
//            return;
//        }
//
//        for (int i = startIndex; i < locations.size(); i++) {
//            Location location = locations.get(i);
//            if (!location.taken && location.weight <= remainingCapacity) {
//                List<Location> newCurrent = new ArrayList<>(current);
//                newCurrent.add(location);
//
//                findBestDelivery(newCurrent, locations, capacity, i + 1);
//            }
//        }
//
//        if (bestDelivery.isEmpty() || currentWeight > bestDelivery.stream().mapToInt(l -> l.weight).sum()) {
//            bestDelivery = current;
//        }
//    }
//}

public class DroneDeliveryServiceTest {

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 0) {
            System.out.println("Please provide an input data file.");
            System.exit(0);
        }

        //Scanner scanner = new Scanner(DroneDeliveryServiceTest.class.getClassLoader().getResource(args[0]).getFile());

        List<Drone> drones = new ArrayList<>();
        ArrayList<Location> locations = new ArrayList<>();

        // Stub input data
        String inputData = "[DroneA] 300 [DroneB] 350 [DroneC] 200 " +
                "[LocationA] 200 [LocationB] 150 [LocationC] 50 " +
                "[LocationD] 150 [LocationE] 100 [LocationF] 200 " +
                "[LocationG] 50 [LocationH] 80 [LocationI] 70 [LocationJ] 50";

        Scanner scanner = new Scanner(inputData);

        while (scanner.hasNext()) {
            String itemType = scanner.next();

            if (itemType.startsWith("[Drone")) {
                drones.add(new Drone(itemType, scanner.nextInt()));
            } else if (itemType.startsWith("[Location")) {
                locations.add(new Location(itemType, scanner.nextInt()));
            }
        }
        scanner.close();

        // Process deliveries
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