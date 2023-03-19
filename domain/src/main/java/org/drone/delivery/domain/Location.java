package org.drone.delivery.domain;
public class Location {
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

    String name;
    int weight;

    public Location(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }
}