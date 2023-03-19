package org.drone.delivery.service;

import java.io.IOException;

public interface DroneDeliveryService {
    void execute(String[] args) throws IOException;
}
