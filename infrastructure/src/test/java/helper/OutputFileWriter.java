package helper;

import org.junit.jupiter.api.Test;
import usecase._01DroneDeliveryServiceFirstConceptTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OutputFileWriter {
    @Test
    public void whenWriteWithFileWriter_thenCorrect() throws IOException {
        String outputSample = "[DroneA]\n" +
                "Trip #1\n" +
                "[LocationI], [LocationJ], [LocationM], [LocationN]\n" +
                "[DroneB]\n" +
                "Trip #1\n" +
                "[LocationA]\n" +
                "Trip #2\n" +
                "[LocationB], [LocationC]\n" +
                "Trip #3\n" +
                "[LocationD], [LocationE]\n" +
                "Trip #4\n" +
                "[LocationF], [LocationG]\n" +
                "Trip #5\n" +
                "[LocationH], [LocationK], [LocationL], [LocationO], [LocationP]\n" +
                "[DroneC]";

        List<String> outputMessages = Arrays.stream(outputSample.split("\n")).toList();

        String file = "output.txt";
        Path filePath = Paths.get(getClass().getClassLoader().getResource(file).getFile());

        // Write output messages to file
        try (FileWriter writer = new FileWriter(filePath.toFile());
             BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            for (String message : outputMessages) {
                writer.write(message);
                writer.write(System.lineSeparator());
            }
            // Assert that the file was written correctly
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                assertEquals(outputMessages.get(i), line);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception thrown during test");
        }
    }
}
