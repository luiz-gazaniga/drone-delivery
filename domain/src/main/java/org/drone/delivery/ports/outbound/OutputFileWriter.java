package org.drone.delivery.ports.outbound;

import org.drone.delivery.domain.Drone;

import java.util.List;

public interface OutputFileWriter {
    void writeResults(String[] args, List<Drone> drones);
}
