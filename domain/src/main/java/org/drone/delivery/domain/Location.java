package org.drone.delivery.domain;
public class Location {
    private String name;
    private int weight;

    public Location(String name, int weight) {
        this.setName(name);
        this.setWeight(weight);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}