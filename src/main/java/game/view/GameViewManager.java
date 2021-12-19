package game.view;


import com.google.gson.Gson;

import game.ConnectedGameController;
import game.HostGameController;
import game.client.PlayerClient;
import game.data.LevelData;
import game.model.GameLabel;
import game.view.models.Player;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class GameViewManager {

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private Scene gameScene;
    private Pane gamePane;
    private ArrayList<Node> platforms = new ArrayList<>();
    private Player player;
    private GameLabel timer;
    private GameLabel firstPlayerScores;
    private GameLabel secondPlayerScores;
    private static GameViewManager gm;
    private PlayerClient client;
    private HostGameController hostGameController;
    private ConnectedGameController connectedGameController;


    public GameViewManager(boolean isConnected) {
        initGameContent();
        if (!isConnected) {
            hostGameController = new HostGameController(gamePane, platforms, player, timer, firstPlayerScores, keys, client, secondPlayerScores);
            hostGameController.startGame();
        } else {
            connectedGameController = new ConnectedGameController(gamePane, platforms, player, timer, firstPlayerScores, keys, client, secondPlayerScores);
            connectedGameController.connectToHost();
        }
    }

    public PlayerClient getClient() {
        return client;
    }

    private void initGameContent() {
        client = new PlayerClient(this);
        try {
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gamePane = new Pane();
        gameScene = new Scene(gamePane, 1024, 800);
        gameScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        Image bg = new Image("background_main_menu.png", 1024, 800, false, true);
        BackgroundImage background = new BackgroundImage(bg, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));
        player = new Player(createEntity(300, 200, 40, 40, new Image("cat.png")));

        createMap();
        createTimer();
        createScores();

        Gson gson = new Gson();
        HashMap<String, String> message = new HashMap<>();
        message.put("method", "new");
        message.put("parameter", "Johnny");
        client.sendMessage(gson.toJson(message) + "\n");
    }

    private void createTimer() {
        timer = new GameLabel("0.00", 60);
        timer.setTranslateX(460);
        timer.setTranslateY(0);
        timer.setText("02.00");

        gamePane.getChildren().add(timer);
    }

    private void createScores() {
        firstPlayerScores = new GameLabel("0", 60);
        firstPlayerScores.setTranslateX(350);
        firstPlayerScores.setTranslateY(0);
        secondPlayerScores = new GameLabel("0", 60);
        secondPlayerScores.setTranslateX(600);
        secondPlayerScores.setTranslateY(0);

        gamePane.getChildren().add(firstPlayerScores);
        gamePane.getChildren().add(secondPlayerScores);

    }

    private void createMap() {
        for (int i = 0; i < LevelData.MAP_1.length; i++) {
            String line = LevelData.MAP_1[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Node grass = createEntity(j * 60, i * 60, 60, 60, new Image("grass.png"));
                        platforms.add(grass);
                        break;
                    case '2':
                        Node wood = createEntity(j * 60, i * 60, 60, 60, new Image("wood.png"));
                        platforms.add(wood);
                        break;
                }
            }
        }
    }

    private Node createEntity(int x, int y, int w, int h, Image image) {
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(new ImagePattern(image));

        gamePane.getChildren().add(entity);
        return entity;
    }

    public synchronized void exitGame() {
        javafx.application.Platform.runLater(Platform::exit);
    }

    public Scene getGameScene() {
        return gameScene;
    }

    public HostGameController getHostGameController() {
        return hostGameController;
    }

    public ConnectedGameController getConnectedGameController() {
        return connectedGameController;
    }

    public static GameViewManager getGm() {
        return gm;
    }

    public static void setGm(GameViewManager gm) {
        GameViewManager.gm = gm;
    }
}
