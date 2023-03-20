package org.drone.delivery.infrastructure.output;
import org.drone.delivery.domain.Drone;
import org.drone.delivery.ports.outbound.OutputFileWriter;
import org.drone.delivery.ports.outbound.OutputWriter;
import java.io.File;
import java.util.Comparator;
import java.util.List;

public class OutputFileWriterImpl implements OutputFileWriter {
    private static final String DEBUG_FLAG = "debug";
    private static final String OUTPUT_FILE_NAME = "_output.txt";

    public void writeResults(String[] args, List<Drone> drones) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Input arguments cannot be null or empty");
        }
        File file = new File(args[0]);
        if (!file.exists()) {
            throw new IllegalArgumentException("Input file does not exist");
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException("Input path does not represent a file");
        }
        if (!file.getName().endsWith(".txt")) {
            throw new IllegalArgumentException("Input file is not a valid text file");
        }
        String filePath = String.format("%s/%s", file.getParent(),
                file.getName().replace(".txt", OUTPUT_FILE_NAME));

        OutputWriter outputWriter = getOutputWriter(args);
        drones.sort(Comparator.comparing((Drone d) -> d.getName()));
        outputWriter.writeResults(filePath, drones);
    }

    private OutputWriter getOutputWriter(String[] args) {
        if (isDebugEnabled(args)) {
            return new ConsoleOutputWriter();
        } else {
            return new FileOutputWriter();
        }
    }

    private boolean isDebugEnabled(String[] args) {
        return args.length == 2 && args[1].equals(DEBUG_FLAG);
    }
}