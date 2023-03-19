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
    /**
     * Assigns delivery locations to drones based on their weight capacity.
     *
     * @param drones    The list of drones to assign deliveries to.
     * @param locations The list of delivery locations to be assigned to the drones.
     */
    private void assignDeliveries(List<Drone> drones, List<Location> locations) {
        // Sort the locations list in ascending order based on their weight.
        locations.sort(Comparator.comparingInt((Location l) -> l.getWeight()));

        // Sort the drones list in descending order based on their maximum weight capacity.
        drones.sort(Comparator.comparingInt((Drone d) -> d.getMaxWeight()).reversed());

        // Assign delivery locations to each drone until all locations are assigned.
        while (!locations.isEmpty()) {
            for (Drone drone : drones) {
                List<Location> currentTrip = new ArrayList<>();
                int currentWeight = 0;
                for (Location location : new ArrayList<>(locations)) {
                    //checking whether the weight of the current delivery location, when added to the weight of
                    // previously assigned locations in the currentTrip list, is less than or equal to the maximum
                    // weight capacity of the current drone.
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
