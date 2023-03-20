package org.drone.delivery.domain.test;

import org.drone.delivery.domain.Drone;
import org.drone.delivery.domain.InputData;
import org.drone.delivery.domain.Location;
import org.drone.delivery.ports.outbound.InputFileParser;
import org.drone.delivery.ports.outbound.OutputFileWriter;
import org.drone.delivery.service.DroneDeliveryService;
import org.drone.delivery.service.DroneDeliveryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class DroneDeliveryServiceImplTest {
    @InjectMocks
    private DroneDeliveryService droneDeliveryService;
    @Mock
    private InputFileParser inputFileParser;
    @Mock
    private OutputFileWriter outputFileWriter;

    @BeforeEach
    public void setUp() {
        inputFileParser = mock(InputFileParser.class);
        outputFileWriter = mock(OutputFileWriter.class);
        droneDeliveryService = new DroneDeliveryServiceImpl(inputFileParser, outputFileWriter);
    }

    @Test
    void testInitilizeDrones() throws IOException {
        // Set up test data
        String[] args = new String[]{"input.txt"};

        List<Drone> expectedDrones = new ArrayList<>();
        Drone droneA = new Drone("DroneA", 200);
        Drone droneB = new Drone("DroneB", 250);
        Drone droneC = new Drone("DroneC", 100);
        expectedDrones.add(droneA);
        expectedDrones.add(droneB);
        expectedDrones.add(droneC);

        List<Location> expectedLocations = new ArrayList<>();
        expectedLocations.add(new Location("LocationA", 200));
        expectedLocations.add(new Location("LocationB", 150));
        expectedLocations.add(new Location("LocationC", 50));
        expectedLocations.add(new Location("LocationD", 150));
        expectedLocations.add(new Location("LocationE", 100));
        expectedLocations.add(new Location("LocationF", 200));
        expectedLocations.add(new Location("LocationG", 50));
        expectedLocations.add(new Location("LocationH", 80));
        expectedLocations.add(new Location("LocationI", 70));
        expectedLocations.add(new Location("LocationJ", 50));
        expectedLocations.add(new Location("LocationK", 30));
        expectedLocations.add(new Location("LocationL", 20));
        expectedLocations.add(new Location("LocationM", 50));
        expectedLocations.add(new Location("LocationN", 30));
        expectedLocations.add(new Location("LocationO", 20));
        expectedLocations.add(new Location("LocationP", 90));

        InputData inputData = new InputData(expectedDrones, expectedLocations);

        // Set up mock behavior
        doReturn(inputData).when(inputFileParser).parse(args);

        // Call the method under test
        droneDeliveryService.execute(args);

        // Verify that the outputFileWriter was called with the expected arguments
        verify(outputFileWriter).writeResults(eq(args), anyList());
    }

    @Test
    void testAssignDeliveries() throws IOException {
        String[] args = new String[]{"input.txt"};
        // Arrange
        Drone drone1 = new Drone("1", 10);
        Drone drone2 = new Drone("2", 20);
        List<Drone> drones = new ArrayList<>();
        drones.add(drone1);
        drones.add(drone2);

        Location location1 = new Location("1", 5);
        Location location2 = new Location("2", 10);
        Location location3 = new Location("3", 15);
        List<Location> locations = new ArrayList<>();
        locations.add(location1);
        locations.add(location2);
        locations.add(location3);
        int totalLocations = locations.size();

        InputData inputData = new InputData(drones, locations);

        // Set up mock behavior
        doReturn(inputData).when(inputFileParser).parse(args);

        // Act
        droneDeliveryService.execute(args);

        // Assert
        List<List<Location>> trips1 = drone1.getTrips();
        List<List<Location>> trips2 = drone2.getTrips();
        int totalLocationsAssigned = 0;
        for (List<Location> trip : trips1) {
            totalLocationsAssigned += trip.size();
        }
        for (List<Location> trip : trips2) {
            totalLocationsAssigned += trip.size();
        }
        Assertions.assertEquals(totalLocations, totalLocationsAssigned);
    }
}