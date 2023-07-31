package com.codecool.marsexploration.ui;

import com.codecool.marsexploration.logic.MapConfiguration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInputTest {

    @Test
    void getConfiguration() {
        UserInput userInput = new UserInput();
        MapConfiguration config = userInput.getConfiguration();

        assertNotNull(config);

        assertEquals("filename", config.filename());
        assertEquals(50, config.mapWidth());
        assertEquals(1, config.mountainCount());
        assertEquals(2, config.pitCount());
        assertEquals(5, config.mineralCount());
        assertEquals(4, config.waterCount());
    }
}