package com.ShootGame.game;


import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class PlayerMovementClass extends MapClass{
    
    protected int health;
    protected int armor;
    
    public Pane player(double width, double height){

        Rectangle player = new Rectangle(25,25);
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
    private void updateCrosshair(Pane player, Circle crosshair, double mouseX, double mouseY, double distance) {
    double playerX = player.getLayoutX() + player.getWidth() / 2;
    double playerY = player.getLayoutY() + player.getHeight() / 2;

    // direction vector (mouse - player)
    double dx = mouseX - playerX;
    double dy = mouseY - playerY;

    // normalize
    double length = Math.sqrt(dx * dx + dy * dy);
    if (length != 0) {
        dx /= length;
        dy /= length;
    }

    // place crosshair
    double crosshairX = playerX + dx * distance;
    double crosshairY = playerY + dy * distance;
    crosshair.setCenterX(crosshairX);
    crosshair.setCenterY(crosshairY);

    // optional rotate player towards crosshair
    double angle = Math.toDegrees(Math.atan2(dy, dx));
    player.setRotate(angle);
}
    
}
