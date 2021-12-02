package game;

import game.view.GameViewManager;
import game.view.models.Player;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameController {
    private ArrayList<Node> foodEntities;
    private ArrayList<Node> platforms;
    private Pane gamePane;
    private Player player;
    private int playerScore;

    public GameController (Pane gamePane, ArrayList<Node> platforms, Player player) {
        this.gamePane = gamePane;
        this.platforms = platforms;
        this.player = player;
        playerScore = 0;
    }




    public void startGame() {
        spawnFood();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                eatFood();
            }
        };
        timer.start();
    }

    private void spawnFood() {
        foodEntities = createFoodEntities();
        for(Node entity: foodEntities) {

            Timeline spawnEntity = new Timeline(
                    new KeyFrame(Duration.seconds(4 + (int) (Math.random() * 180)),
                            new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent event) {
                                    gamePane.getChildren().add(entity);

                                }
                            }));
//        spawnEntity.setCycleCount(Timeline.INDEFINITE);
            spawnEntity.play();
        }

    }

    private Node createEntityWithoutAdding(int x, int y, int w, int h, Color color) {
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);

        return entity;
    }

    public ArrayList<Node> createFoodEntities() {
        ArrayList<Node> foodEntities = new ArrayList<>();
        for(Node platform : platforms) {
            if (platform.getTranslateX() > 0 && platform.getTranslateY() > 0
                    && platform.getTranslateX() < 920 && platform.getTranslateY() < 740) {
                if (Math.random() < 0.5) {
                    Node food = createEntityWithoutAdding((int) platform.getTranslateX() + 40, (int) platform.getTranslateY() - 20 , 20,20, Color.YELLOW);
                    foodEntities.add(food);
                }

            }
        }
        return foodEntities;
    }

    private void eatFood() {
        for (Node food : foodEntities) {
            if (player.getPlayerNode().getBoundsInParent().intersects(food.getBoundsInParent())) {
                gamePane.getChildren().remove(food);
                playerScore += 1;
                System.out.println(1);
            } else {
                System.out.println(222);
            }
        }
    }

}
