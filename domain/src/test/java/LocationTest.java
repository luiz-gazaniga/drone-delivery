import org.drone.delivery.domain.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocationTest {

    @Test
    public void testGetName() {
        Location location = new Location("LocationA", 100);
        Assertions.assertEquals("LocationA", location.getName());
    }

    @Test
    public void testSetName() {
        Location location = new Location("LocationA", 100);
        location.setName("LocationB");
        Assertions.assertEquals("LocationB", location.getName());
    }

    @Test
    public void testGetWeight() {
        Location location = new Location("LocationA", 100);
        Assertions.assertEquals(100, location.getWeight());
    }

    @Test
    public void testSetWeight() {
        Location location = new Location("LocationA", 100);
        location.setWeight(200);
        Assertions.assertEquals(200, location.getWeight());
    }

}