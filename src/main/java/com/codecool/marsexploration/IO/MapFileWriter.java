package com.codecool.marsexploration.IO;

import java.io.FileWriter;
import java.io.IOException;

public class MapFileWriter {
    public static void writeMapToFile(String filename, String[][] map) throws IOException {
        FileWriter writer = new FileWriter(filename);
        for (String[] strings : map) {
            for (String string : strings) {
                writer.write(string + " ");
            }
            writer.write("\n");
        }
        writer.close();
    }
}
