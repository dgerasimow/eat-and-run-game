package game;

import game.model.EndGameSubscene;
import game.view.models.Player;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameController {

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private CopyOnWriteArrayList<Node> foodEntities;
    private ArrayList<Node> platforms;
    private Pane gamePane;
    private Player player;
    private int playerScore;
    private Label timerLabel;
    private Label firstPlayerScores;
    private int[] time = {120};
    private boolean isTimeIsUp = false;
    private EndGameSubscene endGameSubscene;
    private Scene gameScene;

    public GameController (Pane gamePane, ArrayList<Node> platforms, Player player, Label timer, Label firstPlayerScores, HashMap<KeyCode, Boolean> keys, Scene gameScene) {
        this.gamePane = gamePane;
        this.platforms = platforms;
        this.player = player;
        playerScore = 0;
        timerLabel = timer;
        this.firstPlayerScores = firstPlayerScores;
        endGameSubscene = new EndGameSubscene();
        this.keys = keys;
        this.gameScene = gameScene;
    }



    public void startGame() {
        setGameTimer();
        spawnFood();
        foodEntities = new CopyOnWriteArrayList<>();
        System.out.println("GAME BEGINS");
        AnimationTimer mainGameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                eatFood();
            }
        };
        mainGameTimer.start();

        AnimationTimer updateTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        updateTimer.start();

        AnimationTimer timeTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (isTimeIsUp) {
                    gameEnding();
                    updateTimer.stop();
                    this.stop();
                }
            }
        };
        timeTimer.start();

    }

    private void gameEnding() {
        endGameSubscene.setLayoutX(285);
        endGameSubscene.setLayoutY(150);
        gamePane.getChildren().add(endGameSubscene);
    }

    private void setGameTimer() {
        Timeline gameTimer = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        actionEvent -> {
                            int minutes = (time[0] % 3600) / 60;
                            int seconds = time[0] % 60;
                            timerLabel.setText(String.format("%02d.%02d", minutes, seconds));
                            time[0]--;
                        }
                ));
        gameTimer.setCycleCount(121);
        gameTimer.play();
    }

    private void spawnFood() {
            Timeline spawnEntity = new Timeline(
                    new KeyFrame(Duration.seconds(4 + (int) (Math.random() * 10)),
//                    new KeyFrame(Duration.seconds(1),
                            new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent event) {
                                    Node food = createFoodEntitiy();
                                    gamePane.getChildren().add(food);
                                    foodEntities.add(food);
                                    System.out.println(foodEntities);
                                    System.out.println("ADDED NEW FOOD");

                                }
                            }));
            spawnEntity.setCycleCount(Timeline.INDEFINITE);

        AnimationTimer spawnTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (time[0] <= -1) {
                    isTimeIsUp = true;
                    spawnEntity.stop();
                    this.stop();
                }
            }
        };
        spawnTimer.start();
        spawnEntity.play();

    }

    private Node createEntityWithoutAdding(int x, int y, int w, int h, Color color) {
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);

        return entity;
    }

    public Node createFoodEntitiy() {
//        CopyOnWriteArrayList<Node> foodEntities = new CopyOnWriteArrayList<>();
        Node food = null;
        for(Node platform : platforms) {
            if (platform.getTranslateX() > 0 && platform.getTranslateY() > 0
                    && platform.getTranslateX() < 920 && platform.getTranslateY() < 740) {
                if (Math.random() < 0.5) {
                    food = createEntityWithoutAdding((int) platform.getTranslateX() + 40, (int) platform.getTranslateY() - 20 , 20,20, Color.YELLOW);
                    Collections.shuffle(platforms);
                    break;
                }
            }
        }
        return food;
    }

    private void eatFood() {
        if (foodEntities.size() != 0) {
            for (Node food : foodEntities) {
                if (player.getPlayerNode().getBoundsInParent().intersects(food.getBoundsInParent())) {
                    gamePane.getChildren().remove(food);
                    playerScore += 1;
                    firstPlayerScores.setText(Integer.toString(playerScore));
                    foodEntities.remove(food);
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

}
