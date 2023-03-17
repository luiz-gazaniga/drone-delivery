import org.drone.delivery.domain.Drone;
import org.drone.delivery.domain.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DroneTest {

    @Test
    public void testPlanTripWithNoLocations() {
        Drone drone = new Drone("DroneA", 300);
        List<Location> locations = new ArrayList<>();

        List<Location> result = drone.planTrip(locations);

        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void testPlanTripWithOneLocationBelowMaxWeight() {
        Drone drone = new Drone("DroneA", 300);
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("LocationA", 100));

        List<Location> result = drone.planTrip(locations);

        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(locations.containsAll(result));
    }

    @Test
    public void testPlanTripWithOneLocationAboveMaxWeight() {
        Drone drone = new Drone("DroneA", 300);
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("LocationA", 500));

        List<Location> result = drone.planTrip(locations);

        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void testPlanTripWithMultipleLocations() {
        Drone drone = new Drone("DroneA", 500);
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("LocationA", 200));
        locations.add(new Location("LocationB", 150));
        locations.add(new Location("LocationC", 100));
        locations.add(new Location("LocationD", 50));

        List<Location> result = drone.planTrip(locations);

        Assertions.assertEquals(4, result.size());
        Assertions.assertTrue(result.contains(locations.get(0)));
        Assertions.assertTrue(result.contains(locations.get(1)));
        Assertions.assertTrue(result.contains(locations.get(2)));
        Assertions.assertTrue(result.stream().mapToInt(Location::getWeight).sum() <= drone.getMaxWeight());
    }
}