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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class DroneDeliveryServiceImplTest {
    private InputFileParser inputFileParser;
    private OutputFileWriter outputFileWriter;
    private DroneDeliveryService droneDeliveryService;

    @BeforeEach
    public void setUp() {
        inputFileParser = mock(InputFileParser.class);
        outputFileWriter = mock(OutputFileWriter.class);
        droneDeliveryService = new DroneDeliveryServiceImpl(inputFileParser, outputFileWriter);
    }

    @Test
    public void testExecute() throws IOException {
        // Arrange
        String[] args = {"input.txt"};
        InputData inputData = new InputData(Arrays.asList(new Drone("1", 10), new Drone("2", 20)),
                Arrays.asList(new Location("1", 5), new Location("2", 10), new Location("3", 15)));

        when(inputFileParser.parse(args)).thenReturn(inputData);

        // Act
        droneDeliveryService.execute(args);

        // Assert
        verify(inputFileParser).parse(args);
        verify(outputFileWriter).writeResults(args, inputData.getDrones());
    }

    @Test
    public void testAssignDeliveries() {
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

        // Act

        //TODO: use mockito to send parameter to the methods.
        //droneDeliveryService.execute();

        // Assert
        Assertions.assertEquals(1, drone1.getTrips().size());
        Assertions.assertEquals(2, drone2.getTrips().size());
        Assertions.assertEquals(location1, drone1.getTrips().get(0).get(0));
        Assertions.assertEquals(location2, drone2.getTrips().get(0).get(0));
        Assertions.assertEquals(location3, drone2.getTrips().get(1).get(0));
    }
}