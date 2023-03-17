package org.drone.delivery.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Drone {
    private String name;
    private int maxWeight;

    public Drone(String name, int maxWeight) {
        this.setName(name);
        this.setMaxWeight(maxWeight);
    }

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

    public List<Location> planTrip(List<Location> locations) {
        // Sort locations in descending order of weight
        Collections.sort(locations, Comparator.comparing(Location::getWeight).reversed());

        // Case where there is only one location with weight below max weight
        if (locations.size() == 1 && locations.get(0).getWeight() <= maxWeight) {
            return locations;
        }

        int[] dp = new int[maxWeight + 1];
        int n = locations.size();
        // fill the dynamic programming array by iterating through each location and considering whether to include it in the selected locations
        for (int i = 0; i < n; i++) {
            for (int j = maxWeight; j >= locations.get(i).getWeight(); j--) {
                // if including the current location results in a higher total weight, update the dynamic programming array
                dp[j] = Math.max(dp[j], dp[j - locations.get(i).getWeight()] + locations.get(i).getWeight());
            }
        }

        ArrayList<Location> selectedLocations = new ArrayList<>();
        int remainingCapacity = maxWeight;

        // iterate through the locations in reverse order to build the list of selected locations
        for (int i = n - 1; i >= 0; i--) {
            // if the current location was selected, add it to the list of selected locations and update the remaining capacity
            if (remainingCapacity >= locations.get(i).getWeight() &&
                    dp[remainingCapacity] - dp[remainingCapacity - locations.get(i).getWeight()] == locations.get(i).getWeight()) {
                selectedLocations.add(locations.get(i));
                remainingCapacity -= locations.get(i).getWeight();
            }
        }

        return selectedLocations;
    }
}