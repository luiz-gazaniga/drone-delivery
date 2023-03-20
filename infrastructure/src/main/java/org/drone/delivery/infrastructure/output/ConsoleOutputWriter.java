package org.drone.delivery.infrastructure.output;

import org.drone.delivery.domain.Drone;
import org.drone.delivery.domain.Location;
import org.drone.delivery.ports.outbound.OutputWriter;
import java.util.List;

class ConsoleOutputWriter implements OutputWriter {

    @Override
    public void writeResults(String filePath, List<Drone> drones) {
        System.out.println("Output representation of the file " + filePath);
        for (Drone drone : drones) {
            System.out.println("[" + drone.getName() + "]");

            for (int i = 0; i < drone.getTrips().size(); i++) {
                System.out.println("Trip #" + (i + 1));
                List<Location> locations = drone.getTrips().get(i);
                locations.forEach(location -> System.out.println("[" + location.getName() + "]"));
            }

            System.out.println();
        }
    }
}
