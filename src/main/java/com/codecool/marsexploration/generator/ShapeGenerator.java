package com.codecool.marsexploration.generator;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.ObjectOnMap;
import com.codecool.marsexploration.data.Rectangle;

import java.util.*;

public class ShapeGenerator {

    private static Random random = new Random();

    public ShapeGenerator() {
        random = new Random();
    }

    public static List<Coordinate> generateShape(int size) {
        List<Coordinate> shape = new ArrayList<>();
        int a = random.nextInt(size - size/5);
        int b = random.nextInt(size - size/5);
        Coordinate firstCoordinate = new Coordinate(a, b);
        shape.add(firstCoordinate);

        addNeighbors(shape, size);
        for(Coordinate element : shape){
            shape.set(shape.indexOf(element),new Coordinate(element.x() - a,element.y() - b));
        }
        return shape;
    }
public static Boolean overlapXY(ObjectOnMap candidateObj, ObjectOnMap objToCompareTo){
        Rectangle candidateRect = candidateObj.getRectangle();
        Coordinate o1 = candidateRect.getUpperLeft();
        int l1 = candidateRect.getLength();
        int candidateWidth = candidateRect.getWidth();
        Rectangle rectToCompareTo = objToCompareTo.getRectangle();
        Coordinate o2 = rectToCompareTo.getUpperLeft();
        int l2 = rectToCompareTo.getLength();
        int ovrX = 0;
        int ovrY = 0;
        int widthToCompareTo = rectToCompareTo.getWidth();
        if((o1.y() < o2.y() + l2) && (o1.x() + l1 > o2.x())){
            ovrX = o2.x() - o1.x() - l1;
            ovrY = o2.y() + l2 - o1.y();
        }
        if((o1.y() < o2.y() + l2) && (o2.x() + l2 > o1.x())){
            ovrX = o2.x() + l2 - o1.x();
            ovrY = o2.y() + l2 - o1.y();
        }
        if((o2.y() < o1.y() + l1) && (o1.x() + l1 > o2.x())){
            ovrX = o2.x() - o1.x() - l1;
            ovrY = o2.y() - o1.y() - l1;
        }
        if((o2.y() < o1.y() + l1) && (o2.x() + l2 > o1.x())){
            ovrX = o1.x() - o2.x() - l2;
            ovrY = o1.y() - o2.y() + l1;
        }
//Coordinate newCoord = new Coordinate(ovrX, ovrY);
//        if(ovrX !=0 || ovrY !=0){
//            return true;
//        }
//        else return false;
return false;

}
public static Rectangle getRectangleFromShape(List<Coordinate> shape){
        int minX = 0;
        int maxX = 0;
        int maxY = 0;
        int minY = 0;
        for(Coordinate coord : shape){
            if(coord.x() < minX){
                minX = coord.x();
            }
            if(coord.x() > maxX){
                maxX = coord.x();
            }
            if(coord.y() < minY){
                minY = coord.y();
            }
            if(coord.y() > maxY){
                maxY = coord.y();
            }
        }
        int length = maxX - minX;
        int width = maxY - minY;
        Coordinate origin = new Coordinate(0,0);
        return new Rectangle(origin, length, width);
}
    private static void addNeighbors(List<Coordinate> shape, int size) {
        //getCandidates
        List<Coordinate> candidates = new ArrayList<>();
        for (Coordinate coordinate : shape) {
            List<Coordinate> neighbors = getNeighbors(coordinate, size);
            for (Coordinate neighbor : neighbors) {
                if (!shape.contains(neighbor) && !candidates.contains(neighbor)) {
                    candidates.add(neighbor);
                }
            }
        }
        if (!candidates.isEmpty() && shape.size() < size) {
            Coordinate newCoordinate = candidates.get(random.nextInt(candidates.size()));
            shape.add(newCoordinate);
            addNeighbors(shape, size);
        }
    }

    private static List<Coordinate> getNeighbors(Coordinate coordinate, int size) {
        List<Coordinate> neighbors = new ArrayList<>();
        int x = coordinate.x();
        int y = coordinate.y();
        if (x > 0) {
            neighbors.add(new Coordinate(x - 1, y));
        }
        if (x < size - 1) {
            neighbors.add(new Coordinate(x + 1, y));
        }
        if (y > 0) {
            neighbors.add(new Coordinate(x, y - 1));
        }
        if (y < size - 1) {
            neighbors.add(new Coordinate(x, y + 1));
        }
        return neighbors;
    }

    public static String[][] placeShapeOnMatrix(String[][] matrix, List<Coordinate> shape, String symbol) {
        for (Coordinate coordinate : shape) {
            matrix[coordinate.y()][coordinate.x()] = symbol + " ";
        }
        return matrix;
    }
    public static List<Coordinate> getOuterLayer(List<Coordinate> shape, int size) {
        List<Coordinate> outerLayer = new ArrayList<>();
        for (Coordinate coordinate : shape) {
            List<Coordinate> neighbors = getNeighbors(coordinate, size);
            for (Coordinate neighbor : neighbors) {
                if (!shape.contains(neighbor) && !outerLayer.contains(neighbor)) {
                    outerLayer.add(neighbor);
                }
            }
        }
        return outerLayer;
    }

}
