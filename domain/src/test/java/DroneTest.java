import org.drone.delivery.domain.Drone;
import org.drone.delivery.domain.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DroneTest {
    @Test
    public void testGetName() {
        Drone drone = new Drone("Drone A", 100);
        Assertions.assertEquals("Drone A", drone.getName());
    }

    @Test
    public void testSetName() {
        Drone drone = new Drone("Drone A", 100);
        drone.setName("Drone B");
        Assertions.assertEquals("Drone B", drone.getName());
    }

    @Test
    public void testGetMaxWeight() {
        Drone drone = new Drone("Drone A", 100);
        Assertions.assertEquals(100, drone.getMaxWeight());
    }

    @Test
    public void testSetMaxWeight() {
        Drone drone = new Drone("Drone A", 100);
        drone.setMaxWeight(200);
        Assertions.assertEquals(200, drone.getMaxWeight());
    }

    @Test
    public void testGetTrips() {
        Drone drone = new Drone("Drone A", 100);
        Assertions.assertNotNull(drone.getTrips());
    }

    @Test
    public void testSetTrips() {
        Drone drone = new Drone("Drone A", 100);
        List<List<Location>> trips = new ArrayList<>();
        drone.setTrips(trips);
        Assertions.assertEquals(trips, drone.getTrips());
    }

    @Test
    public void testConstructor() {
        Drone drone = new Drone("Drone A", 100);
        Assertions.assertEquals("Drone A", drone.getName());
        Assertions.assertEquals(100, drone.getMaxWeight());
        Assertions.assertNotNull(drone.getTrips());
    }
}