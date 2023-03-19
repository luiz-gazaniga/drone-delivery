package org.drone.delivery.output;

import org.drone.delivery.domain.Drone;

import java.util.List;

public class OutputFileWriter {
    public static void writeResults(String[] args, List<Drone> drones) {
        boolean debug = args.length == 2 && args[1].equals("debug");
        for (Drone drone : drones) {
            if (debug) System.out.println("[" + drone.getName() + "]");
            for (int i = 0; i < drone.getTrips().size(); i++) {
                if (debug) System.out.println("Trip #" + (i + 1));
                if (debug) drone.getTrips().get(i).forEach(location -> System.out.println("[" + location.getName() + "]"));
            }
            if (debug) System.out.println();
        }
    }
}
