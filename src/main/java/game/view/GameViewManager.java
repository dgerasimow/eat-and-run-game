package game.view;


import com.google.gson.Gson;
import game.HostGameController;
import game.client.PlayerClient;
import game.data.LevelData;
import game.model.GameLabel;
import game.view.models.EnemyPlayer;
import game.view.models.Player;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class GameViewManager {
    //TODO: refactor this shit

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private Scene gameScene;
    private Stage rootStage;
    private Pane gamePane;
    private ArrayList<Node> platforms = new ArrayList<>();
    private Player player;
    private EnemyPlayer enemy;
    private GameLabel timer;
    private GameLabel firstPlayerName;
    private GameLabel secondPlayerName;
    private GameLabel firstPlayerScores;
    private GameLabel secondPlayerScores;
    private static GameViewManager gm = new GameViewManager();
    private PlayerClient client;
    private HostGameController hostGameController;

    public static GameViewManager getGm() {
        return gm;
    }

    public GameViewManager() {
        initGameContent();
        hostGameController = new HostGameController(gamePane,platforms, player, timer, firstPlayerScores, keys, gameScene, enemy);
        hostGameController.startGame();
    }
    private void initGameContent(){
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
        Image bg = new Image("background_main_menu.png", 1024,800, false, true);
        BackgroundImage background = new BackgroundImage(bg, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));
        player = new Player(createEntity(300, 200, 40, 40, Color.AQUAMARINE),"Johnny");
        createMap(LevelData.MAP_1);
        createTimer();
        createScores();

        Gson gson = new Gson();
        HashMap<String,String> message = new HashMap<>();
        message.put("method", "new");
        message.put("parameter","Johnny");
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

        gamePane.getChildren().add(firstPlayerScores);

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

    public synchronized void createEnemy() {
        if (enemy == null) {
            javafx.application.Platform.runLater(() -> {
                System.out.println("created Enemy");
                enemy = new EnemyPlayer(createEntity(500, 200, 40, 40, Color.RED), "Ivan");
                hostGameController.setEnemy(enemy);
            });
        }
    }

    public EnemyPlayer getEnemy() {
        return enemy;
    }
}
