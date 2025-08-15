package com.ShootGame.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameEventLogic extends Application {

    @Override
    public void start(Stage stage) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                System.out.println("Tick: " + now);
            }
        };
        timer.start();

        // Close app after a short time for testing
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {}
            timer.stop();
            System.exit(0);
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}