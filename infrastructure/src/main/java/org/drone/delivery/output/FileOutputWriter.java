package org.drone.delivery.output;

import org.drone.delivery.domain.Drone;
import org.drone.delivery.domain.Location;
import org.drone.delivery.ports.outbound.OutputWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

class FileOutputWriter implements OutputWriter {
    @Override
    public void writeResults(String filePath, List<Drone> drones) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Drone drone : drones) {
                writer.write("[" + drone.getName() + "]");
                writer.newLine();

                for (int i = 0; i < drone.getTrips().size(); i++) {
                    writer.write("Trip #" + (i + 1));
                    writer.newLine();
                    List<Location> locations = drone.getTrips().get(i);
                    for (Location location : locations) {
                        writer.write("[" + location.getName() + "]");
                        writer.newLine();
                    }
                }

                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to output file: " + e.getMessage());
        }
    }
}