package org.drone.delivery.domain.test;

import org.drone.delivery.domain.Drone;
import org.drone.delivery.domain.InputData;
import org.drone.delivery.domain.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class InputDataTest {

    @Test
    public void testGetDrones() {
        List<Drone> drones = new ArrayList<>();
        drones.add(new Drone("DroneA", 200));
        drones.add(new Drone("DroneB", 300));
        InputData inputData = new InputData(drones, new ArrayList<>());

        Assertions.assertEquals(drones, inputData.getDrones());
    }

    @Test
    public void testGetLocations() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("LocationA", 100));
        locations.add(new Location("LocationB", 150));
        InputData inputData = new InputData(new ArrayList<>(), locations);

        Assertions.assertEquals(locations, inputData.getLocations());
    }

    @Test
    public void testGetDronesWithNoDrones() {
        InputData inputData = new InputData(new ArrayList<>(), new ArrayList<>());

        Assertions.assertEquals(new ArrayList<>(), inputData.getDrones());
    }

    @Test
    public void testGetLocationsWithNoLocations() {
        InputData inputData = new InputData(new ArrayList<>(), new ArrayList<>());

        Assertions.assertEquals(new ArrayList<>(), inputData.getLocations());
    }
}