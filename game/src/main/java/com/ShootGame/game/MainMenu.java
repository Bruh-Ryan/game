package com.ShootGame.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class MainMenu {

    private Stage stage;
    private double width;
    private double height;
    private boolean paused = false;
    private StackPane resumeMenu;
    private boolean sfx = true;
    private boolean music = true;

    public MainMenu(Stage stage, double width, double height) {
        this.stage = stage;
        this.width = width;
        this.height = height;
        Menu();
    }

    public void Menu() {
    // Main menu vertical layout
        VBox menuItemsBox= new VBox(20);
        Button startButton = new Button("Start");
        Button optionsButton = new Button("Options");
        
        menuItemsBox.getChildren().addAll(startButton,optionsButton);
        menuItemsBox.setAlignment(Pos.CENTER);

    // Toggle buttons horizontal layout
        HBox toggleBox = new HBox(20);
        Button soundButton = new Button("SFX");
        Button musicButton = new Button("Music");
        toggleBox.getChildren().addAll(soundButton,musicButton);
        toggleBox.setPadding(new Insets(10));
        toggleBox.setAlignment(Pos.BOTTOM_CENTER);

    // stack layout combines both
        StackPane stack = new StackPane();
        stack.getChildren().addAll(menuItemsBox,toggleBox);
        StackPane.setAlignment(menuItemsBox, Pos.CENTER);
        StackPane.setAlignment(toggleBox, Pos.BOTTOM_CENTER);
        toggleBox.setPickOnBounds(false);//allows the layer over to pass through
        stack.setStyle("-fx-background-color: yellowgreen;");
        stack.setBackground(null);


    // Create scene with stack
        Scene scene = new Scene(stack ,width , height,Color.YELLOWGREEN);
      
        stage.setScene(scene);
        stage.show();

        //Events:
        startButton.setOnMouseClicked(event -> {
            System.out.println("Start Clicked");
            GameLogic(stage);
        });
        optionsButton.setOnMouseClicked(event -> {
            System.out.println("Options Clicked");
            
        });
        soundButton.setOnMouseClicked(event -> {
            System.out.println("Sound Clicked");
            
        });
        musicButton.setOnMouseClicked(event -> {
            System.out.println("Music Clicked");
           
        });


    }

    public void GameLogic(Stage gameStage) {

    // Use a Pane for absolute positioning
    Pane gridLayer = new Pane();
    gridLayer.setStyle("-fx-background-color: yellow;");

    // StackPane for pause menu layering
    StackPane gameRoot = new StackPane(gridLayer);

    Scene scene = new Scene(gameRoot, width, height);
    stage.setScene(scene);
    stage.show();

    // GamePlay Logics
    MapClass gameMap = new MapClass();
    gameMap.showMap(gridLayer, width, height, 1);

    PlayerMovementClass p = new PlayerMovementClass();
    Pane player = p.player(width, height);
    gridLayer.getChildren().add(player);

    // events----------------------------------

    resumeMenu = createResumeWindow(gameRoot);
    // Initialize map as final to satisfy lambda expression requirements
    final HashMap<String, Boolean> map = gameMap.mapBounds(1);

    // Add calculations for map positioning and bounds checking
    final double cellSize = 60;
    int[][] matrix = gameMap.requestMap(1);
    final double startX = (width - matrix[0].length * cellSize) / 2;
    final double startY = (height - matrix.length * cellSize) / 2;
    final int rows = matrix.length;
    final int cols = matrix[0].length;

    scene.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ESCAPE) {
            if (!paused) {
                gameRoot.getChildren().add(resumeMenu);
                paused = true;
                System.out.println("Pause menu opened");
            } else {
                gameRoot.getChildren().remove(resumeMenu);
                paused = false;
                System.out.println("Pause menu closed");
            }
        }

        if (!paused) {
            double deltaX = 0;
            double deltaY = 0;
            switch (event.getCode()) {
                case W:
                    deltaY = -cellSize;
                    break;
                case A:
                    deltaX = -cellSize;
                    break;
                case S:
                    deltaY = cellSize;
                    break;
                case D:
                    deltaX = cellSize;
                    break;
                default:
                    return; // Ignore other keys
            }
            // Calculate current and target positions, accounting for any existing translate
            double currX = player.getLayoutX() + player.getTranslateX();
            double currY = player.getLayoutY() + player.getTranslateY();
            double targetX = currX + deltaX;
            double targetY = currY + deltaY;

            // Convert target position to grid row and column
            int targetRow = (int) Math.floor((targetY - startY) / cellSize);
            int targetCol = (int) Math.floor((targetX - startX) / cellSize);

            // Check if target is within map bounds and walkable using the HashMap:
            // The HashMap 'map' stores keys as "row,col" strings, with true if the cell is walkable (matrix[row][col] == 1, e.g., floor)
            // and false if not (e.g., obstacle). We query map.get(targetRow + "," + targetCol) to determine if movement is allowed.
            // This avoids direct matrix access and uses the precomputed bounds for quick lookup.
            if (targetRow >= 0 && targetRow < rows && 
                targetCol >= 0 && targetCol < cols && 
                Boolean.TRUE.equals(map.get(targetRow + "," + targetCol))) {
                p.playerMove(player, deltaX, deltaY);
            }
        }
    });
    //     scene.setOnMouseMoved(event -> {
    //         double mouseX = event.getSceneX(); // mouse position in scene
    //         double mouseY = event.getSceneY();
    //         System.out.println("Mouse moved to: " + mouseX + ", " + mouseY);
    // });

    // test events trails:---------------------------------TESTING CODE-------------------------------------------------------------------------
    
    // System.out.println(Arrays.toString(gameWorld.showCenterMap(width, height, 1)));
    System.out.println(player.getLayoutX());
    System.out.println(Arrays.toString(p.showCenterMap(width, height, 1)));
    
    // iterate keys
    for (String key : map.keySet()) {
        System.out.println("Key: " + key + " Value: " + map.get(key));
    }

    // iterate entries (best way)
    for (Map.Entry<String, Boolean> entry : map.entrySet()) {
        System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
    }

    //cursorcode trials
            // hide system cursor
scene.setCursor(Cursor.NONE);

// make crosshair
Circle crosshair = new Circle(5, Color.RED);
gridLayer.getChildren().add(crosshair);

// distance from player to crosshair
double aimDistance = 100;

scene.setOnMouseMoved(event -> {
    double mouseX = event.getSceneX();
    double mouseY = event.getSceneY();

    // center of player
    double playerX = player.getLayoutX() + player.getWidth() / 2;
    double playerY = player.getLayoutY() + player.getHeight() / 2;

    // direction vector (mouse - player)
    double dx = mouseX - playerX;
    double dy = mouseY - playerY;

    // normalize vector
    double length = Math.sqrt(dx * dx + dy * dy);
    if (length != 0) {
        dx /= length;
        dy /= length;
    }

    // place crosshair at fixed distance from player
    double crosshairX = playerX + dx * aimDistance;
    double crosshairY = playerY + dy * aimDistance;

    crosshair.setCenterX(crosshairX);
    crosshair.setCenterY(crosshairY);

    // optional: rotate player towards crosshair
    double angle = Math.toDegrees(Math.atan2(dy, dx));
    player.setRotate(angle);
});

    //----------------------------------------------------------------------------------------------------------------------------
}

    private StackPane createResumeWindow(StackPane gameRoot) {

        System.out.println("In Resume Window");
        StackPane menu = new StackPane();
        menu.setStyle("-fx-background-color: yellowgreen;");
        menu.setOpacity(0.95);
        
        Button resumeButton = new Button("Resume");
        Button mainMenuButton = new Button("Exit");
        VBox resumeBox= new VBox(20);
        resumeBox.getChildren().addAll(resumeButton, mainMenuButton);
        resumeBox.setAlignment(Pos.CENTER);

        //Events:
        resumeButton.setOnAction(e -> {
            gameRoot.getChildren().remove(menu);
            paused = false;
            System.out.println("Pause menu closed (via button)");
            });
        mainMenuButton.setOnAction(e -> {
            gameRoot.getChildren().remove(menu);
            paused = false;
            System.out.println("Pause menu closed (via button)");
            Menu();
        });

        menu.getChildren().add(resumeBox);
        return menu;
    }

}


