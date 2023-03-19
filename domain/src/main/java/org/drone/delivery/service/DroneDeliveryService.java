package org.drone.delivery.service;

import java.io.IOException;

public interface DroneDeliveryService {
    /**
     * Assigns delivery locations to drones based on their weight capacity.
     *
     * @param args The first parameter is mandatory and represents the path of the input file. The second
     *             parameter is an optional parameter 'debug'. If this parameter is provided, the application
     *             will display the output on the console instead of writing it to a file. If the second
     *             parameter is not provided, the application will write the output to a file named
     *             'INPUT_FILE_NAME]_OUTPUT.txt' in the same folder as the input file.
     */
    void execute(String[] args) throws IOException;
}
