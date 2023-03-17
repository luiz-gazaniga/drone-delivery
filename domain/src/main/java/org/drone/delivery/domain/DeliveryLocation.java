package org.drone.delivery.domain;

public class DeliveryLocation {
    private String name;
    private int weight;

    public DeliveryLocation(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }
}