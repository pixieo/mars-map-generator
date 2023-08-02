package com.codecool.marsexploration.ui;

import com.codecool.marsexploration.logic.MapConfiguration;

import javax.swing.*;

public class UserInput {
    public MapConfiguration getConfiguration() {
        MapConfiguration mapConfiguration = null;
        boolean isValid = false;

        while (!isValid){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JTextField filenameField = new JTextField(10);
            JTextField mapWidthField = new JTextField(10);
            JTextField mountainCountField = new JTextField(10);
            JTextField pitCountField = new JTextField(10);
            JTextField mineralCountField = new JTextField(10);
            JTextField waterCountField = new JTextField(10);

            panel.add(new JLabel("Filename:"));
            panel.add(filenameField);
            panel.add(new JLabel("Map width:"));
            panel.add(mapWidthField);
            panel.add(new JLabel("Number of mountains:"));
            panel.add(mountainCountField);
            panel.add(new JLabel("Number of pits:"));
            panel.add(pitCountField);
            panel.add(new JLabel("Number of minerals:"));
            panel.add(mineralCountField);
            panel.add(new JLabel("Number of bodies of water:"));
            panel.add(waterCountField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Enter Map Configuration",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {

                if (mapWidthField.getText().matches("\\d+") &&
                        mountainCountField.getText().matches("\\d+") &&
                        pitCountField.getText().matches("\\d+") &&
                        mineralCountField.getText().matches("\\d+") &&
                        waterCountField.getText().matches("\\d+")
                ) {
                    String filename = filenameField.getText();
                    int mapWidth = Integer.parseInt(mapWidthField.getText());
                    int mountainCount = Integer.parseInt(mountainCountField.getText());
                    int pitCount = Integer.parseInt(pitCountField.getText());
                    int mineralCount = Integer.parseInt(mineralCountField.getText());
                    int waterCount = Integer.parseInt(waterCountField.getText());

                    int[] mountainSizes = getSizes("Enter Mountain Size", "Size of mountain", mountainCount);
                    int[] pitSizes = getSizes("Enter Pit Size", "Size of pit", pitCount);

                    mapConfiguration = new MapConfiguration(filename, mapWidth, mountainCount, mountainSizes, pitCount, pitSizes, mineralCount, waterCount);
                    isValid = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input!");
                }
            } else {
                break;
            }
        }
        return mapConfiguration;
    }

    private int[] getSizes(String title, String labelPrefix, int count) {
        int[] sizes = new int[count];
        for(int i = 0; i < count; i++) {
            JTextField sizeField = new JTextField(10);
            JPanel sizePanel = new JPanel();
            sizePanel.add(new JLabel(labelPrefix + (i + 1) + ":"));
            sizePanel.add(sizeField);
            int result = JOptionPane.showConfirmDialog(null, sizePanel, title,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION && sizeField.getText().matches("\\d+")) {
                sizes[i] = Integer.parseInt(sizeField.getText());
            } else {
                JOptionPane.showMessageDialog(null, "Invalid input for " +labelPrefix.toLowerCase() + " size!");
                break;
            }
        }
        return sizes;
    }


}
