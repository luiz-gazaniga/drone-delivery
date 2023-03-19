package usecase.multipleknapsack;

import org.drone.delivery.ports.outbound.InputFileParser;
import org.drone.delivery.input.InputFileParserImpl;
import org.drone.delivery.output.OutputFileWriterImpl;
import org.drone.delivery.ports.outbound.OutputFileWriter;
import org.drone.delivery.service.DroneDeliveryService;
import org.drone.delivery.service.DroneDeliveryServiceImpl;

import java.io.IOException;

public class MultipleKnapsackDroneDeliveryServiceRefactorTest {
    public static void main(String[] args) throws IOException {
        execute(args);
    }

    private static void execute(String[] args) throws IOException {
        InputFileParser inputFileParser = new InputFileParserImpl();
        OutputFileWriter outputFileWriter = new OutputFileWriterImpl();

        DroneDeliveryService droneDeliveryService = new DroneDeliveryServiceImpl(inputFileParser, outputFileWriter);
        droneDeliveryService.execute(args);
    }
}