package org.drone.delivery.ports.inbound;

import java.io.IOException;

public interface DroneDeliveryServicePort {
    void execute(String[] args) throws IOException;
}
