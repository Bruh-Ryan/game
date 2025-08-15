package com.ShootGame.game;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Survivor");

        MainMenu startGame = new MainMenu(primaryStage, 840.0, 580.0);
        startGame.Menu(); 
    }

    public static void main(String[] args) {
        launch(args);
    }
}
