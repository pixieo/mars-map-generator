package com.codecool.marsexploration.IO;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
class MapFileWriterTest {

    @Test
    void writeMapToFile() throws IOException {
        String fileName = "map-test.txt";
        String[][] map = {{"#", " ", " "},{"#", "#", "~"}, {"#", " ", "^"}};

        MapFileWriter.writeMapToFile(fileName, map);

        String expectedFileContents = "#     \n# # ~ \n#   ^ \n";
        Path pathToFile = Paths.get(fileName);
        String actualFileContents = new String(Files.readAllBytes(pathToFile));

        assertEquals(expectedFileContents, actualFileContents);

        Files.deleteIfExists(pathToFile);
    }
}