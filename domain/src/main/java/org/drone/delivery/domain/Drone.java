package org.drone.delivery.domain;

import java.util.ArrayList;
import java.util.List;

public class Drone {
    private String name;
    int maxWeight;

    public Drone(String name, int maxWeight) {
        this.setName(name);
        this.maxWeight = maxWeight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Location> planTrip(List<Location> locations) {
        int[] dp = new int[maxWeight + 1];
        int n = locations.size();

        for (int i = 0; i < n; i++) {
            for (int j = maxWeight; j >= locations.get(i).getWeight(); j--) {
                dp[j] = Math.max(dp[j], dp[j - locations.get(i).getWeight()] + locations.get(i).getWeight());
            }
        }

        ArrayList<Location> selectedLocations = new ArrayList<>();
        int remainingCapacity = maxWeight;

        for (int i = n - 1; i >= 0; i--) {
            if (remainingCapacity >= locations.get(i).getWeight() &&
                    dp[remainingCapacity] - dp[remainingCapacity - locations.get(i).getWeight()] == locations.get(i).getWeight()) {
                selectedLocations.add(locations.get(i));
                remainingCapacity -= locations.get(i).getWeight();
            }
        }

        return selectedLocations;
    }
}