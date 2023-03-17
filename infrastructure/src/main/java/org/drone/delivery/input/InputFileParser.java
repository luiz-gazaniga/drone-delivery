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
        List<Drone> drones = new ArrayList<>();
        List<Location> locations = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            Matcher droneMatcher = DRONE_PATTERN.matcher(line);
            Matcher locationMatcher = LOCATION_PATTERN.matcher(line);

            if (droneMatcher.find()) {
                String droneName = droneMatcher.group(1);
                int droneCapacity = Integer.parseInt(droneMatcher.group(2));
                drones.add(new Drone(droneName, droneCapacity));
            } else if (locationMatcher.find()) {
                String locationName = locationMatcher.group(1);
                int locationWeight = Integer.parseInt(locationMatcher.group(2));
                locations.add(new Location(locationName, locationWeight));
            }
        }
        reader.close();

        return new InputData(drones, locations);
    }
}
