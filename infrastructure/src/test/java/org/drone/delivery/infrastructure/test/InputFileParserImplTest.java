package org.drone.delivery.infrastructure.test;

import org.drone.delivery.domain.InputData;
import org.drone.delivery.infrastructure.input.InputFileParserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class InputFileParserImplTest {

    private InputFileParserImpl parser;

    @Mock
    private BufferedReader reader;

    @BeforeEach
    public void setUp() {
        parser = new InputFileParserImpl();
    }

    @Test
    public void testParse() throws IOException {
        Path tempFile = Files.createTempFile("test-file", ".txt");
        tempFile.toFile().deleteOnExit();

        String content = "[Drone1, 5], [Location1, 7]";
        Files.write(tempFile, content.getBytes());

        InputData inputData = parser.parse(new String[]{tempFile.toString()});

    }

    @Test
    public void testParseWithInvalidItemType() throws IOException {
        Path tempFile = Files.createTempFile("test-file", ".txt");
        tempFile.toFile().deleteOnExit();

        String content = "This is the content of the file";
        Files.write(tempFile, content.getBytes());

        assertThrows(IllegalArgumentException.class, () -> parser.parse(new String[]{tempFile.toString()}));
    }

    @Test
    public void testParseWithEmptyInput() throws IOException {
        Path tempFile = Files.createTempFile("test-file", ".txt");
        tempFile.toFile().deleteOnExit();

        String content = "";
        Files.write(tempFile, content.getBytes());

        assertThrows(IllegalArgumentException.class, () -> parser.parse(new String[]{"test-file.txt"}));
    }

    @Test
    public void testValidateArgs_NullArgs() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(null));
    }

    @Test
    public void testValidateArgs_EmptyArgs() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(new String[]{}));
    }

    @Test
    public void testValidateArgs_NonexistentFile() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(new String[]{"nonexistent-file.txt"}));
    }

    @Test
    public void testValidateArgs_NotAFile() throws IOException {
        Path tempDirectory = Files.createTempDirectory("test-directory");
        tempDirectory.toFile().deleteOnExit();

        assertThrows(IllegalArgumentException.class, () -> parser.parse(new String[]{tempDirectory.toString()}));
    }

    @Test
    public void testValidateArgs_NotATextFile() throws IOException {
        Path tempFile = Files.createTempFile("test-file", ".bin");
        tempFile.toFile().deleteOnExit();

        assertThrows(IllegalArgumentException.class, () -> parser.parse(new String[]{tempFile.toString()}));
    }
}
