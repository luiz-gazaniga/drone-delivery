package org.drone.delivery.infrastructure.input;

import org.drone.delivery.domain.Drone;
import org.drone.delivery.domain.InputData;
import org.drone.delivery.domain.Location;
import org.drone.delivery.ports.outbound.InputFileParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputFileParserImpl implements InputFileParser {
    // Constants for strings used in the class
    private static final String DRONE_PREFIX = "Drone";
    private static final String LOCATION_PREFIX = "Location";
    private static final String REGEX_TO_REMOVE_BRACKETS = "[\\[\\](){}]";
    private static final String ITEM_SEPARATOR = ", ";
    private static final String EMPTY_STRING = "";

    public InputData parse(String[] args) throws IOException {
        validateArgs(args);
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));

        List<Drone> drones = new ArrayList<>();
        List<Location> locations = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            // Split the line into tokens using the defined separator
            String[] tokens = line.split(ITEM_SEPARATOR);
            if (tokens.length < 2)
                throw new IllegalArgumentException("Invalid input content.");
            for (int i = 0; i < tokens.length; i += 2) {
                String itemType = tokens[i].substring(1).replaceAll(REGEX_TO_REMOVE_BRACKETS, EMPTY_STRING);
                int itemValue = Integer.parseInt(tokens[i + 1].replaceAll(REGEX_TO_REMOVE_BRACKETS, EMPTY_STRING));

                // Validate and create Drone or Location objects based on the itemType
                if (itemType.startsWith(DRONE_PREFIX)) {
                    drones.add(new Drone(itemType, itemValue));
                } else if (itemType.startsWith(LOCATION_PREFIX)) {
                    locations.add(new Location(itemType, itemValue));
                } else {
                    throw new IllegalArgumentException("Invalid item type: " + itemType);
                }
            }
        }
        reader.close();

        // Validate that there are at least one drone and one location
        if (drones.isEmpty() || locations.isEmpty()) {
            throw new IllegalArgumentException("Input file must contain at least one drone and one location.");
        }

        return new InputData(drones, locations);
    }

    private static void validateArgs(String[] args) {
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
    }
}