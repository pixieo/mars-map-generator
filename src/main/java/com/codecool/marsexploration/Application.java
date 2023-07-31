package com.codecool.marsexploration;

import com.codecool.marsexploration.IO.MapFileWriter;
import com.codecool.marsexploration.generator.MapGenerator;
import com.codecool.marsexploration.logic.MapConfiguration;
import com.codecool.marsexploration.ui.UserInput;
import java.io.IOException;
public class Application {
    public static void main(String[] args) {
        UserInput userInput = new UserInput();
        MapConfiguration mapConfiguration = userInput.getConfiguration();
        MapGenerator mapGenerator = new MapGenerator(mapConfiguration);
        try {
            mapGenerator.generateMap(mapConfiguration, 1000);

            MapFileWriter.writeMapToFile(mapConfiguration.filename(), mapGenerator.generateMap(mapConfiguration, 1000));
            System.out.println("Map saved to " + mapConfiguration.filename());
        } catch (IOException e) {
            System.out.println("Error saving map: " + e.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Error: " + illegalArgumentException.getMessage());
        }
    }
}
