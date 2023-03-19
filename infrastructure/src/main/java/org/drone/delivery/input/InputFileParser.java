package org.drone.delivery.input;

import org.drone.delivery.domain.Drone;
import org.drone.delivery.domain.InputData;
import org.drone.delivery.domain.Location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFileParser {
    private static final Pattern DRONE_PATTERN = Pattern.compile("\\[(Drone\\w)]\\s*,\\s*\\[(\\d+)]");
    private static final Pattern LOCATION_PATTERN = Pattern.compile("\\[(Location\\w)]\\s*,\\s*\\[(\\d+)]");

    public static InputData parse(String filePath) throws IOException {
        BufferedReader reader = new
                BufferedReader(new FileReader(filePath));

        List<Drone> drones = new ArrayList<>();
        List<Location> locations = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(", ");
            for (int i = 0; i < tokens.length; i += 2) {
                String itemType = tokens[i].substring(1).replaceAll("[\\[\\](){}]", "");
                int itemValue = Integer.parseInt(tokens[i + 1].replaceAll("[\\[\\](){}]", ""));
                if (itemType.startsWith("Drone")) {
                    drones.add(new Drone(itemType, itemValue));
                } else if (itemType.startsWith("Location")) {
                    locations.add(new Location(itemType, itemValue));
                }
            }
        }
        reader.close();

        return new InputData(drones, locations);
    }
}
