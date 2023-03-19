package org.drone.delivery.domain;

import java.util.ArrayList;
import java.util.List;

public class Drone {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public List<List<Location>> getTrips() {
        return trips;
    }

    public void setTrips(List<List<Location>> trips) {
        this.trips = trips;
    }

    int maxWeight;
    List<List<Location>> trips;

    public Drone(String name, int maxWeight) {
        this.name = name;
        this.maxWeight = maxWeight;
        this.trips = new ArrayList<>();
    }
}