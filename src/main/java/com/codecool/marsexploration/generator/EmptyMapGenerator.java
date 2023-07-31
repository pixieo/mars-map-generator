package com.codecool.marsexploration.generator;

public class EmptyMapGenerator {
    public static String[][] generateEmptyMap(int width) {
        String[][] map = new String[width][width];

        for (int i = 0; i < width; i++) {
            String generatedMap = "";

            for (int j = 0; j < width; j++){
                map[i][j] = " ";
                generatedMap += map[i][j] + " ";
            }
        }
        return map;
    }
}
