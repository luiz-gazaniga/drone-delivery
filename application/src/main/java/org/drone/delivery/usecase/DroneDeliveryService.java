package org.drone.delivery.usecase;

import org.drone.delivery.domain.DeliveryLocation;
import org.drone.delivery.domain.Drone;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DroneDeliveryService {
    public void run(List<Drone> drones, List<DeliveryLocation> locations) {
        drones.sort(Comparator.comparingInt(Drone::getMaxWeight).reversed());
        locations.sort(Comparator.comparingInt(DeliveryLocation::getWeight).reversed());

        for (Drone drone : drones) {
            List<DeliveryLocation> availableLocations = new ArrayList<>(locations);
            System.out.println("[" + drone.getName() + "]");
            int tripCount = 1;

            while (!availableLocations.isEmpty()) {
                List<DeliveryLocation> tripLocations = new ArrayList<>();
                int currentWeight = 0;

                for (int i = 0; i < availableLocations.size(); i++) {
                    DeliveryLocation location = availableLocations.get(i);
                    if (currentWeight + location.getWeight() <= drone.getMaxWeight()) {
                        tripLocations.add(location);
                        currentWeight += location.getWeight();
                        availableLocations.remove(i);
                        i--;
                    }
                }

                if (tripLocations.isEmpty()) {
                    break;
                }

                System.out.println("Trip #" + tripCount);
                tripCount++;
                for (DeliveryLocation location : tripLocations) {
                    System.out.println("[" + location.getName() + "]");
                    locations.remove(location);
                }
            }
        }

    }
}