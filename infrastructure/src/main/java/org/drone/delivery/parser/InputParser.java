package org.drone.delivery.parser;

import org.drone.delivery.domain.DeliveryLocation;
import org.drone.delivery.domain.Drone;
import java.util.ArrayList;
import java.util.List;

public class InputParser {
    public List<Drone> parseDrones(String[] droneInputs) {
        List<Drone> drones = new ArrayList<>();
        for (int i = 0; i < droneInputs.length; i += 2) {
            String name = droneInputs[i];
            int maxWeight = Integer.parseInt(droneInputs[i + 1]);
            drones.add(new Drone(name, maxWeight));
        }
        return drones;
    }

    public List<DeliveryLocation> parseDeliveryLocations(String[] locationInputs) {
        List<DeliveryLocation> locations = new ArrayList<>();
        for (int i = 0; i < locationInputs.length; i += 2) {
            String name = locationInputs[i];
            int weight = Integer.parseInt(locationInputs[i + 1]);
            locations.add(new DeliveryLocation(name, weight));
        }
        return locations;
    }
}