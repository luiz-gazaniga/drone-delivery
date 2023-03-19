package org.drone.delivery.service;

import org.drone.delivery.domain.Drone;
import org.drone.delivery.domain.InputData;
import org.drone.delivery.domain.Location;
import org.drone.delivery.ports.outbound.InputFileParser;
import org.drone.delivery.ports.outbound.OutputFileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DroneDeliveryServiceImpl implements DroneDeliveryService {

    private InputFileParser inputFileParser;
    private OutputFileWriter outputFileWriter;
    public DroneDeliveryServiceImpl(InputFileParser inputFileParser, OutputFileWriter outputFileWriter){
        this.inputFileParser = inputFileParser;
        this.outputFileWriter = outputFileWriter;
    }

    public void execute(String[] args) throws IOException {
        InputData data = this.inputFileParser.parse(args);
        assignDeliveries(data.getDrones(), data.getLocations());
        this.outputFileWriter.writeResults(args, data.getDrones());
    }
    private void assignDeliveries(List<Drone> drones, List<Location> locations) {
        locations.sort(Comparator.comparingInt((Location l) -> l.getWeight()));
        drones.sort(Comparator.comparingInt((Drone d) -> d.getMaxWeight()).reversed());

        while (!locations.isEmpty()) {
            for (Drone drone : drones) {
                List<Location> currentTrip = new ArrayList<>();
                int currentWeight = 0;
                for (Location location : new ArrayList<>(locations)) {
                    if (currentWeight + location.getWeight() <= drone.getMaxWeight()) {
                        currentWeight += location.getWeight();
                        currentTrip.add(location);
                        locations.remove(location);
                    }
                }
                if (!currentTrip.isEmpty()) {
                    drone.getTrips().add(currentTrip);
                }
            }
        }
    }
}
