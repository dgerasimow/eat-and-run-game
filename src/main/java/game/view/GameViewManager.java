package game.view;


import game.GameController;
import game.data.LevelData;
import game.view.models.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class GameViewManager {
    //TODO: refactor this shit

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();

    private Scene gameScene;
    private Pane gamePane;
    private ArrayList<Node> platforms = new ArrayList<>();
    private Player player;


    public GameViewManager() {
        initGameContent();
        GameController game = new GameController();
    }
    private void initGameContent() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, 1024, 800);
        gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        Image bg = new Image("background_main_menu.png", 1024,800, false, true);
        BackgroundImage background = new BackgroundImage(bg, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));
        player = new Player(createEntity(300, 200, 40, 40, Color.AQUAMARINE));


        createMap(LevelData.MAP_1);

        createFoodEntities();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        timer.start();
    }

    private void createMap (String[] mapData) {
        for (int i = 0; i < mapData.length; i++) {
            String line = mapData[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Node platform = createEntity(j*60, i*60, 60, 60, Color.PALEVIOLETRED);
                        platforms.add(platform);
                        break;
                }
            }
        }

    }
    private void update() {
        if (isPressed(KeyCode.W) && player.getPlayerNode().getTranslateY() >= 5) {
            player.jumpPlayer();
        }
        if (isPressed(KeyCode.A) && player.getPlayerNode().getTranslateX() >= 5) {
            player.movePlayerX(-5, platforms);
        }
        if (isPressed(KeyCode.D) && player.getPlayerNode().getTranslateX() >= 5 ) {
            player.movePlayerX(5, platforms);
        }
        if (player.getPlayerVelocity().getY() < 10) {
            player.setPlayerVelocity(player.getPlayerVelocity().add(0, 1));
        }

        player.movePlayerY((int) player.getPlayerVelocity().getY(), platforms);
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    private Node createEntity(int x, int y, int w, int h, Color color) {
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);

        gamePane.getChildren().add(entity);
        return entity;
    }

    public ArrayList<Node> createFoodEntities() {
        ArrayList<Node> foodEntities = new ArrayList<>();
        for(Node platform : platforms) {
            if (platform.getTranslateX() > 0 && platform.getTranslateY() > 0
                    && platform.getTranslateX() < 1024 && platform.getTranslateY() < 800) {
                if (Math.random() < 0.5) {
                    Node food = createEntity((int) platform.getTranslateX(), (int) platform.getTranslateY() - 60, 20,20, Color.YELLOW);
                    foodEntities.add(food);
                }

            }
        }
        return foodEntities;
    }

    public Scene getGameScene() {
        return gameScene;
    }

}
