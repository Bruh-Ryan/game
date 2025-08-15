package com.ShootGame.game;


import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class PlayerMovementClass extends MapClass{
    
    protected int health;
    protected int armor;
    
    public Pane player(double width, double height){

        Rectangle player = new Rectangle(30,30);
        Pane playerPane = new Pane();
        MapClass map = new MapClass();
        int[]mapps =map.showCenterMap(width, height, 1);
        playerPane.setLayoutX(mapps[0]);
        playerPane.setLayoutY(mapps[1]);
        playerPane.getChildren().add(player);

        return playerPane ;
    }

    public void playerMove(Pane player, double deltaX, double deltaY){
        TranslateTransition movementTranslateTransition = new TranslateTransition();
        // movementTranslateTransition.setDuration(Duration.millis(200)); // smooth speed
        movementTranslateTransition.setNode(player); // set the Pane to move
        movementTranslateTransition.setByX(deltaX);
        movementTranslateTransition.setByY(deltaY);
        movementTranslateTransition.play();
    }

    public void playerStats(){
        
        health=100;
        armor=100;


    }
    
}