package research.selected.algorithm.greedy;

import org.junit.jupiter.api.Test;

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
public class GreedyDroneDeliveryServiceTest {
    @Test
    public void execute() {
        List<Drone> drones = new ArrayList<>();
        Drone droneA = new Drone("DroneA", 200);
        Drone droneB = new Drone("DroneB", 250);
        Drone droneC = new Drone("DroneC", 100);
        drones.add(droneA);
        drones.add(droneB);
        drones.add(droneC);

        List<Location> locations = new ArrayList<>();
        locations.add(new Location("LocationA", 200));
        locations.add(new Location("LocationB", 150));
        locations.add(new Location("LocationC", 50));
        locations.add(new Location("LocationD", 150));
        locations.add(new Location("LocationE", 100));
        locations.add(new Location("LocationF", 200));
        locations.add(new Location("LocationG", 50));
        locations.add(new Location("LocationH", 80));
        locations.add(new Location("LocationI", 70));
        locations.add(new Location("LocationJ", 50));
        locations.add(new Location("LocationK", 30));
        locations.add(new Location("LocationL", 20));
        locations.add(new Location("LocationM", 50));
        locations.add(new Location("LocationN", 30));
        locations.add(new Location("LocationO", 20));
        locations.add(new Location("LocationP", 90));

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