package org.drone.delivery.ports.outbound;

import org.drone.delivery.domain.Drone;

import java.util.List;

public interface OutputWriter {
    void writeResults(String filePath, List<Drone> drones);
}
