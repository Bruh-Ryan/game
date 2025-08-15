package com.ShootGame.game;

import java.util.HashMap;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MapClass{

    public int[][] requestMap(int map_req) {
        switch (map_req) {
            case 1:
                return new int[][] {
                    {0,1,1,1,0,0,0,0,0,0},
                    {1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1},
                    {0,0,1,1,1,1,1,1,1,1},
                    {0,0,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,0,1,1,1,1},
                    {1,0,1,1,1,1,1,1,1,1},
                    {1,0,1,1,1,0,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1}
                };
            default:
                throw new IllegalArgumentException("Invalid map request: " + map_req);
        }
    }

    public void showMap(Pane gridPane, double sceneWidth, double sceneHeight, int req_map) {
        int cellSize = 60;
        int[][] matrix = requestMap(req_map);

        double startX = (sceneWidth - matrix[0].length * cellSize) / 2;
        double startY = (sceneHeight - matrix.length * cellSize) / 2;

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cell.setFill(matrix[row][col] == 1 ? Color.LIGHTGRAY : Color.BLUE);
                cell.setX(startX + col * cellSize);
                cell.setY(startY + row * cellSize);
                gridPane.getChildren().add(cell);
            }
        }
    }

    public int[] showCenterMap(double sceneWidth, double sceneHeight, int req_map) {
    
    int[][] matrix = requestMap(req_map);

    // Calculate starting coordinates
    double startX = (sceneWidth - matrix[0].length )/2 ;
    double startY = (sceneHeight - matrix.length )/2 ;
    
    
    int[] middle = new int[2];
    middle[0] = (int) Math.round(startX);
    middle[1] = (int) Math.round(startY);

    return middle;
}
public HashMap<String, Boolean> mapHash(int[][] matrix) {
    HashMap<String, Boolean> hash = new HashMap<>();

    for (int row = 0; row < matrix.length; row++) {
        for (int col = 0; col < matrix[row].length; col++) {
            boolean isPassable = (matrix[row][col] == 1);
            hash.put(row + "," + col, isPassable);
        }
    }

    return hash;
}
}