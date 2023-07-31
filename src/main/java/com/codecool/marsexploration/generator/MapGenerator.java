package com.codecool.marsexploration.generator;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.ObjectOnMap;
import com.codecool.marsexploration.data.Rectangle;
import com.codecool.marsexploration.logic.MapConfiguration;

import java.util.*;

import static com.codecool.marsexploration.generator.EmptyMapGenerator.generateEmptyMap;
import static com.codecool.marsexploration.generator.ShapeGenerator.*;

public class MapGenerator {
    private final MapConfiguration configuration;

    public MapGenerator(MapConfiguration configuration) {
        this.configuration = configuration;
    }

    public String[][] generateMap(MapConfiguration configuration, int maxRetries) {

        // Create an empty map with the given width
        Random random = new Random();
        List<ObjectOnMap> metaMap = new ArrayList<>();
        int mapSize = configuration.mapWidth();
        String[][] map = generateEmptyMap(mapSize);

        // class mapValidator
        int totalPossibleElements = mapSize * mapSize;
        int totalShapeElements = Arrays.stream(configuration.mountainSizes()).sum() + Arrays.stream(configuration.pitSizes()).sum();
        int totalResourceElements = configuration.waterCount() + configuration.mineralCount();

        if (totalPossibleElements < totalShapeElements + totalResourceElements) {
            throw new IllegalArgumentException("Invalid map.");
            //System.out.println("Error: The number of shapes and resources is greater than the total possible elements of the map.");
        }

        // Generate the shapes and place them on the map

        String mountainSymbol = "^";
        String pitSymbol = "#";
//mountains
            for(int j = 0; j < configuration.mountainSizes().length; j ++){
               List<Coordinate> mountainShape = generateShape(configuration.mountainSizes()[j] );
                int retries = 1;
                Boolean overlap = true;
                while ((retries <= maxRetries) && (overlap)) {
                    Coordinate coord = new Coordinate(random.nextInt(mapSize - mapSize/5), random.nextInt(mapSize -mapSize/5));
                     overlap = tryPutShapeOnMap(coord, mountainShape, metaMap);
                    // else
                    if(retries == maxRetries){
                        System.out.println("Error: Could not generate map after " + maxRetries + " retries" );
                    }
                    if (!overlap) {
                        for(Coordinate element : mountainShape){
                            mountainShape.set(mountainShape.indexOf(element),new Coordinate(element.x() + coord.x(),element.y() + coord.y()));
                        }
                        List<Coordinate> outerLayer = getOuterLayer(mountainShape, configuration.mountainSizes()[j]);
                        Rectangle mountainRect = getRectangleFromShape(mountainShape);
                        ObjectOnMap metaShape = new ObjectOnMap(coord, mountainSymbol, mountainShape, mountainRect, outerLayer);
                        metaMap.add(metaShape);
                    } else { retries++;
                    }
                }
            }

        //pits
            for(int j = 0; j < configuration.pitSizes().length; j ++){
                List<Coordinate> pitShape = generateShape(configuration.pitSizes()[j]);
                int retries = 1;
                Boolean overlap = true;
                while ((retries <= maxRetries) && overlap) {
                    Coordinate coord = new Coordinate(random.nextInt(mapSize -mapSize/5), random.nextInt(mapSize -mapSize/5));
                     overlap = tryPutShapeOnMap(coord, pitShape, metaMap);
                    // else
                    if(retries == maxRetries){
                        System.out.println("Error: Could not generate map after " + maxRetries + " retries" );
                    }
                    if(!overlap){
                        for(Coordinate element : pitShape){
                            pitShape.set(pitShape.indexOf(element),new Coordinate(element.x() + coord.x(),element.y() + coord.y()));
                        }
                        List<Coordinate> outerLayer = getOuterLayer(pitShape, configuration.pitSizes()[j]);
                        Rectangle pitRect = getRectangleFromShape(pitShape);
                        ObjectOnMap metaShape = new ObjectOnMap(coord, pitSymbol, pitShape, pitRect, outerLayer);
                        metaMap.add(metaShape);
                    }else retries ++;

                }
            }

        //resources
        List<Coordinate> waterList = addWater(metaMap,configuration.waterCount());
        ObjectOnMap water = new ObjectOnMap(null,"~",waterList,null,null);

        List<Coordinate> mineralList = addMinerals(metaMap,configuration.mineralCount());

        ObjectOnMap minerals = new ObjectOnMap(null,"*",mineralList,null,null);
metaMap.add(minerals);
metaMap.add(water);
        return transformObjMapToMap(metaMap, mapSize);
    }
    public String[][] transformObjMapToMap(List<ObjectOnMap> metaObj, int width){
        String[][] map = generateEmptyMap(width);
        for(ObjectOnMap element : metaObj){
            List<Coordinate>list1 = element.getShape();
            System.out.println(list1);
            String symbol = element.getType();
            for (Coordinate el : list1){
                map[Math.abs(el.x())][Math.abs(el.y())] = symbol;
            }
        }
        return map;
    }
    public static List<Coordinate> addWater(List<ObjectOnMap> metaMap, int waterNo){
        List<Coordinate> mountainOuterLayer = null;
        List<Coordinate> pitsOuterLayer = new ArrayList<>();
        List<Coordinate> waterList = new ArrayList<>();
        List<Coordinate> mineralList = null;
        Random random = new Random();

        for(ObjectOnMap obj : metaMap){


           if(Objects.equals(obj.getType(), "#")){



               pitsOuterLayer.addAll(obj.getOuterLayer());
           }
        }
        for(int i = 0; i < waterNo; i++){
            if(!pitsOuterLayer.isEmpty()) {
                int pitsSize = pitsOuterLayer.size();
                int index = random.nextInt(pitsSize);
                int x = pitsOuterLayer.get(index).x();
                int y = pitsOuterLayer.get(index).y();

                waterList.add(new Coordinate(x, y));
            }
        }
        return waterList;
    }
    public static List<Coordinate> addMinerals(List<ObjectOnMap> metaMap, int mineralsNo){
        List<Coordinate> mountainOuterLayer = new ArrayList<>();
        List<Coordinate> pitsOuterLayer = null;
        List<Coordinate> waterList = null;
        List<Coordinate> mineralList = new ArrayList<>();
        Random random = new Random();

        for(ObjectOnMap obj : metaMap){
            if(obj.getType().equals("^")){
                mountainOuterLayer.addAll(obj.getOuterLayer());
            }
        }
        for(int i = 0; i < mineralsNo; i++){
            if (!mountainOuterLayer.isEmpty()) {
                int mountainSize = mountainOuterLayer.size();
                int index = random.nextInt(mountainSize);
                int x = mountainOuterLayer.get(index).x();
                int y = mountainOuterLayer.get(index).y();
                mineralList.add(new Coordinate(x,y));
            }
        }
        return mineralList;
    }
    public static Boolean tryPutShapeOnMap(Coordinate coordinate, List<Coordinate> shape, List<ObjectOnMap> metaMap ){
        Boolean overlap = false;
        Rectangle rect = getRectangleFromShape(shape);
        Coordinate newCoord = coordinate;
        for(ObjectOnMap element : metaMap){
            ObjectOnMap obj1 = new ObjectOnMap(newCoord,null, shape, rect, null);
             overlap = overlapXY(obj1,element);
             // newCoord = new Coordinate(newCoord.x() + overlap.x(), newCoord.y() + overlap.y() );
            if(overlap){break;}
        }
        return overlap;
    }
}


