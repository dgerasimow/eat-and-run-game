package game;

import com.google.gson.Gson;
import game.client.PlayerClient;
import game.model.EndGameSubscene;
import game.view.models.EnemyPlayer;
import game.view.models.Food;
import game.view.models.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConnectedGameController {

    private HashMap<KeyCode, Boolean> keys;
    private CopyOnWriteArrayList<Food> foodEntities;
    private ArrayList<Node> platforms;
    private Pane gamePane;
    private Player player;
    private int firstPlayerScore;
    private int secondPlayerScore;
    private Label timerLabel;
    private Label firstPlayerScores;
    private Label secondPlayerScores;
    private boolean isTimeIsUp = false;
    private EndGameSubscene endGameSubscene;
    private EnemyPlayer enemy;
    private PlayerClient client;
    private Gson gson = new Gson();

    public ConnectedGameController(Pane gamePane, ArrayList<Node> platforms, Player player, Label timer,
                                   Label firstPlayerScores, HashMap<KeyCode, Boolean> keys, PlayerClient client, Label secondPlayerScores) {
        this.gamePane = gamePane;
        this.platforms = platforms;
        this.player = player;
        secondPlayerScore = 0;
        firstPlayerScore = 0;
        timerLabel = timer;
        this.firstPlayerScores = firstPlayerScores;
        this.keys = keys;
        this.client = client;
        this.secondPlayerScores = secondPlayerScores;
    }


    public void connectToHost() {
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
                    gameEnding(checkWinner());
                    updateTimer.stop();
                    this.stop();
                }
            }
        };
        timeTimer.start();

    }

    private void gameEnding(String winner) {
        endGameSubscene = new EndGameSubscene(winner);
        endGameSubscene.setLayoutX(285);
        endGameSubscene.setLayoutY(150);
        gamePane.getChildren().add(endGameSubscene);
    }

    private String checkWinner() {
        if (firstPlayerScore > secondPlayerScore) {
            return "Host player";
        } else if (firstPlayerScore == secondPlayerScore) {
            return "Friendship";
        } else {
            return "Connected player";
        }
    }

    private Node createEntityWithoutAdding(int x, int y, int w, int h, Image image) {
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(new ImagePattern(image));

        return entity;
    }

    private void eatFood() {
        if (foodEntities.size() != 0) {
            for (Food food : foodEntities) {
                if (player.getPlayerNode().getBoundsInParent().intersects(food.getFoodNode().getBoundsInParent())) {
                    HashMap<String, String> message = new HashMap<>();
                    message.put("method", "eat");
                    message.put("foodId", Integer.toString(food.getFoodId()));
                    System.out.println(gson.toJson(message));
                    client.sendMessage(gson.toJson(message) + "\n");
                    gamePane.getChildren().remove(food.getFoodNode());
                    secondPlayerScore += 1;
                    secondPlayerScores.setText(Integer.toString(secondPlayerScore));
                    foodEntities.remove(food);
                }
            }
        }
    }

    public synchronized void enemyEatFood(int foodId) {
        javafx.application.Platform.runLater(() -> {
            for (Food food : foodEntities) {
                if (food.getFoodId() == foodId) {
                    gamePane.getChildren().remove(food.getFoodNode());
                    firstPlayerScore += 1;
                    firstPlayerScores.setText(Integer.toString(firstPlayerScore));
                    foodEntities.remove(food);
                }
            }
        });
    }

    private void update() {
        if (isPressed(KeyCode.W) && player.getPlayerNode().getTranslateY() >= 5) {
            player.jumpPlayer();
        }
        if (isPressed(KeyCode.A) && player.getPlayerNode().getTranslateX() >= 5) {
            player.movePlayerX(-5, platforms);
        }
        if (isPressed(KeyCode.D) && player.getPlayerNode().getTranslateX() >= 5) {
            player.movePlayerX(5, platforms);
        }
        if (player.getPlayerVelocity().getY() < 10) {
            player.setPlayerVelocity(player.getPlayerVelocity().add(0, 1));
        }
        player.movePlayerY((int) player.getPlayerVelocity().getY(), platforms);

        HashMap<String, String> message = new HashMap<>();
        message.put("method", "move");
        message.put("x", Double.toString(player.getPlayerNode().getTranslateX()));
        message.put("y", Double.toString(player.getPlayerNode().getTranslateY()));
        client.sendMessage(gson.toJson(message) + "\n");
    }

    public synchronized void createEnemy() {
        javafx.application.Platform.runLater(() -> {
            if (enemy == null) {
                System.out.println("created Enemy");
                enemy = new EnemyPlayer(createEntityWithoutAdding(500, 200, 40, 40, new Image("enemy_cat.png")));
                gamePane.getChildren().add(enemy.getPlayerNode());
            }
        });
    }

    public synchronized void updateTime(String updatedTime) {
        javafx.application.Platform.runLater(() -> {
            timerLabel.setText(updatedTime);
        });
    }

    public synchronized void moveEnemy(double x, double y) {
        javafx.application.Platform.runLater(() -> {
            enemy.getPlayerNode().setTranslateX(x);
            enemy.getPlayerNode().setTranslateY(y);
        });
    }

    public synchronized void setTimeIsUp() {
        javafx.application.Platform.runLater(() -> {
            isTimeIsUp = true;
        });
    }

    public synchronized void spawnFood(int foodId, double x, double y) {
        javafx.application.Platform.runLater(() -> {
            Food food = new Food(foodId, createEntityWithoutAdding((int) x, (int) y, 20, 20, new Image("food.png")));
            foodEntities.add(food);
            gamePane.getChildren().add(food.getFoodNode());
        });
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    public EnemyPlayer getEnemy() {
        return enemy;
    }
}
