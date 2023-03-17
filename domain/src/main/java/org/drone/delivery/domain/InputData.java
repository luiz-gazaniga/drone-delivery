package org.drone.delivery.domain;

import java.util.List;

public class InputData {
    private List<Drone> drones;
    private List<Location> locations;

    public InputData(List<Drone> drones, List<Location> locations) {
        this.drones = drones;
        this.locations = locations;
    }

    public List<Drone> getDrones() {
        return drones;
    }

    public List<Location> getLocations() {
        return locations;
    }
}

