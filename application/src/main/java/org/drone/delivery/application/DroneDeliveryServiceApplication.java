package org.drone.delivery.application;

import org.drone.delivery.ports.inbound.DroneDeliveryServicePort;
import org.drone.delivery.service.DroneDeliveryService;

import java.io.IOException;

public class DroneDeliveryServiceApplication implements DroneDeliveryServicePort {
    private DroneDeliveryService droneDeliveryService;

    public DroneDeliveryServiceApplication(DroneDeliveryService droneDeliveryService){
        this.droneDeliveryService = droneDeliveryService;
    }
    @Override
    public void execute(String[] args) throws IOException {
        this.droneDeliveryService.execute(args);
    }
}