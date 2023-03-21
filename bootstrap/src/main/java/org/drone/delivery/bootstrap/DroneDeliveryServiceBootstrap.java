package org.drone.delivery.bootstrap;

import org.drone.delivery.application.DroneDeliveryServiceApplication;
import org.drone.delivery.infrastructure.input.InputFileParserImpl;
import org.drone.delivery.infrastructure.output.OutputFileWriterImpl;
import org.drone.delivery.ports.inbound.DroneDeliveryServicePort;
import org.drone.delivery.ports.outbound.InputFileParser;
import org.drone.delivery.ports.outbound.OutputFileWriter;
import org.drone.delivery.service.DroneDeliveryService;
import org.drone.delivery.service.DroneDeliveryServiceImpl;

import java.io.IOException;

public class DroneDeliveryServiceBootstrap {
    public static void main(String[] args) throws IOException {
        InputFileParser inputFileParser = new InputFileParserImpl();
        OutputFileWriter outputFileWriter = new OutputFileWriterImpl();
        DroneDeliveryService droneDeliveryService = new DroneDeliveryServiceImpl(inputFileParser, outputFileWriter);
        DroneDeliveryServicePort droneDeliveryServicePort = new DroneDeliveryServiceApplication(droneDeliveryService);

        droneDeliveryServicePort.execute(args);
    }
}
