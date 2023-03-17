package org.drone.delivery.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputFileReader {
    private final String filePath;

    public InputFileReader(ClassLoader classLoader, String fileName) {
        this.filePath = classLoader.getResource(fileName).getFile();
    }

    public List<String> readLines() throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();
        while (line != null) {
            lines.add(line);
            line = reader.readLine();
        }
        reader.close();
        return lines;
    }
}
