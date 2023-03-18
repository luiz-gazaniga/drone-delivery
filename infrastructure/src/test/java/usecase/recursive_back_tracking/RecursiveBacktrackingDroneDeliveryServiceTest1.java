package usecase.recursive_back_tracking;

import usecase.dynamicprograming.DynamicProgramingDroneDeliveryService1Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Drone {
    String name;
    int maxWeight;
    List<List<Location>> trips;

    public Drone(String name, int maxWeight) {
        this.name = name;
        this.maxWeight = maxWeight;
        this.trips = new ArrayList<>();
    }

    public Drone(Drone other) {
        this.name = other.name;
        this.maxWeight = other.maxWeight;
        this.trips = new ArrayList<>();
        for (List<Location> trip : other.trips) {
            List<Location> newTrip = new ArrayList<>(trip);
            this.trips.add(newTrip);
        }
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

public class RecursiveBacktrackingDroneDeliveryServiceTest1 {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Please provide an input file.");
            return;
        }

        BufferedReader reader = new
                BufferedReader(new FileReader(args[0]));

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

        List<Drone> bestArrangement = findBestArrangement(drones, locations);
        printResults(bestArrangement);
    }

    public static List<Drone> findBestArrangement(List<Drone> drones, List<Location> locations) {
        return findBestArrangementHelper(drones, locations, new HashSet<>());
    }

    static class State {
        List<Drone> drones;
        Set<Location> visited;
        int locationIndex;
        int droneIndex;

        public State(List<Drone> drones, Set<Location> visited, int locationIndex, int droneIndex) {
            this.drones = drones;
            this.visited = visited;
            this.locationIndex = locationIndex;
            this.droneIndex = droneIndex;
        }
    }



    public static List<Drone> replaceDrone(List<Drone> drones, Drone newDrone) {
        List<Drone> newDrones = new ArrayList<>();
        for (Drone drone : drones) {
            if (drone.name.equals(newDrone.name)) {
                newDrones.add(newDrone);
            } else {
                newDrones.add(drone);
            }
        }
        return newDrones;
    }

    public static List<Drone> findBestArrangementHelper(List<Drone> drones, List<Location> locations, Set<Location> visited) {
        if (visited.size() == locations.size()) {
            return drones;
        }

        List<Drone> bestArrangement = null;
        int minTrips = Integer.MAX_VALUE;

        for (Location location : locations) {
            if (!visited.contains(location)) {
                visited.add(location);

                List<Drone> bestLocalArrangement = null;
                int localMinTrips = Integer.MAX_VALUE;

                for (Drone drone : drones) {
                    if (canAddLocationToDrone(drone, location)) {
                        Drone newDrone = new Drone(drone);
                        addLocationToDrone(newDrone, location);
                        List<Drone> newDrones = replaceDrone(drones, newDrone);

                        List<Drone> arrangement = findBestArrangementHelper(newDrones, locations, visited);
                        int totalTrips = getTotalTrips(arrangement);

                        if (totalTrips < localMinTrips) {
                            bestLocalArrangement = arrangement;
                            localMinTrips = totalTrips;
                        }
                    }
                }

                if (bestLocalArrangement != null && localMinTrips < minTrips) {
                    bestArrangement = bestLocalArrangement;
                    minTrips = localMinTrips;
                }

                visited.remove(location);
            }
        }

        return bestArrangement;
    }


    public static boolean canAddLocationToDrone(Drone drone, Location location) {
        if (drone.trips.size() >= 3) {
            return false;
        }

        if (drone.trips.isEmpty()) {
            return location.weight <= drone.maxWeight;
        }

        List<Location> lastTrip = drone.trips.get(drone.trips.size() - 1);
        int currentWeight = lastTrip.stream().mapToInt(l -> l.weight).sum();
        return currentWeight + location.weight <= drone.maxWeight;
    }


    public static void removeLocationFromDrone(Drone drone, Location location) {
        if (!drone.trips.isEmpty()) {
            List<Location> lastTrip = drone.trips.get(drone.trips.size() - 1);
            lastTrip.remove(location);

            if (lastTrip.isEmpty()) {
                drone.trips.remove(drone.trips.size() - 1);
            }
        }
    }

    public static void addLocationToDrone(Drone drone, Location location) {
        if (drone.trips.isEmpty() || !canAddLocationToDrone(drone, location)) {
            drone.trips.add(new ArrayList<>());
        }
        drone.trips.get(drone.trips.size() - 1).add(location);
    }

    public static List<Drone> copyDrones(List<Drone> drones) {
        List<Drone> newDrones = new ArrayList<>();
        for (Drone drone : drones) {
            newDrones.add(new Drone(drone));
        }
        return newDrones;
    }

    public static int getTotalTrips(List<Drone> drones) {
        if (drones == null) {
            return Integer.MAX_VALUE;
        }
        return drones.stream().mapToInt(d -> d.trips.size()).sum();
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