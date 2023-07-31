package com.codecool.marsexploration.data;

import java.util.List;

public class ObjectOnMap {
    private  Coordinate coordinate;
    private String type;
    private List<Coordinate> shape;
    private Rectangle rectangle;
    private List<Coordinate> outerLayer;

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setShape(List<Coordinate> shape) {
        this.shape = shape;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void setOuterLayer(List<Coordinate> outerLayer) {
        this.outerLayer = outerLayer;
    }

    public List<Coordinate> getShape() {
        return shape;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public List<Coordinate> getOuterLayer() {
        return outerLayer;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public String getType() {
        return type;
    }

    public ObjectOnMap(Coordinate coordinate, String type, List<Coordinate> shape, Rectangle rectangle, List<Coordinate> outerLayer) {
        this.coordinate = coordinate;
        this.type = type;
        this.shape = shape;
        this.rectangle = rectangle;
        this.outerLayer = outerLayer;
    }
}
