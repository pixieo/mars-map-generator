package com.codecool.marsexploration.data;

public class Rectangle {
    private Coordinate upperLeft;
    private int length;
    private int width;

    public Coordinate getUpperLeft() {
        return upperLeft;
    }

    public void setUpperLeft(Coordinate upperLeft) {
        this.upperLeft = upperLeft;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public Rectangle(Coordinate upperLeft, int length, int width) {
        this.upperLeft = upperLeft;
        this.length = length;
        this.width = width;
    }
}
