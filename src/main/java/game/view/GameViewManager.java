package game.view;


import game.GameController;
import game.data.LevelData;
import game.model.GameLabel;
import game.view.models.Player;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private GameLabel timer;
    private GameLabel firstPlayerName;
    private GameLabel secondPlayerName;
    private GameLabel firstPlayerScores;
    private GameLabel secondPlayerScores;



    public GameViewManager() {
        initGameContent();
        GameController gameController = new GameController(gamePane,platforms, player);
        gameController.startGame();

    }
    private void initGameContent() {
        gamePane = new Pane();
        gameScene = new Scene(gamePane, 1024, 800);
        gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        Image bg = new Image("background_main_menu.png", 1024,800, false, true);
        BackgroundImage background = new BackgroundImage(bg, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));
        player = new Player(createEntity(300, 200, 40, 40, Color.AQUAMARINE));

        createMap(LevelData.MAP_1);
        createTimer();
        createScores();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        timer.start();
    }

    private void createTimer() {
        timer = new GameLabel("0.00", 60);
        timer.setTranslateX(460);
        timer.setTranslateY(0);

        gamePane.getChildren().add(timer);
    }

    private void createScores() {
        firstPlayerScores = new GameLabel("0", 60);
        firstPlayerScores.setTranslateX(200);
        firstPlayerScores.setTranslateY(0);

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

    public Scene getGameScene() {
        return gameScene;
    }

}
