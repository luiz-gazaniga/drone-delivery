import org.drone.delivery.domain.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocationTest {

    @Test
    public void testGetName() {
        Location location = new Location("Location A", 10);
        Assertions.assertEquals("Location A", location.getName());
    }

    @Test
    public void testSetName() {
        Location location = new Location("Location A", 10);
        location.setName("Location B");
        Assertions.assertEquals("Location B", location.getName());
    }

    @Test
    public void testGetWeight() {
        Location location = new Location("Location A", 10);
        Assertions.assertEquals(10, location.getWeight());
    }

    @Test
    public void testSetWeight() {
        Location location = new Location("Location A", 10);
        location.setWeight(20);
        Assertions.assertEquals(20, location.getWeight());
    }

    @Test
    public void testConstructor() {
        Location location = new Location("Location A", 10);
        Assertions.assertEquals("Location A", location.getName());
        Assertions.assertEquals(10, location.getWeight());
    }
}