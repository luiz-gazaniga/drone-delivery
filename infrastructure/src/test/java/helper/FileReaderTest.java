package helper;

import org.junit.jupiter.api.Test;
import usecase._01DroneDeliveryServiceFirstConceptTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileReaderTest {
    @Test
    public void whenReadWithBufferedReader_thenCorrect()
            throws IOException {
        String expected_value = "[DroneA], [300], [DroneB], [350], [DroneC], [200]";
        String file ="02.txt";

        BufferedReader reader = new
                BufferedReader(new FileReader(_01DroneDeliveryServiceFirstConceptTest.class.getClassLoader().getResource(file).getFile()));
        String currentLine = reader.readLine();
        reader.close();

        assertEquals(expected_value, currentLine);
    }
}
