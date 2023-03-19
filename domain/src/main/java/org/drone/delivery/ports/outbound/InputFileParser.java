package org.drone.delivery.ports.outbound;

import org.drone.delivery.domain.InputData;

import java.io.IOException;

public interface InputFileParser {
    InputData parse(String[] args) throws IOException;
}
