package game.view.models;

import javafx.geometry.Point2D;
import javafx.scene.Node;

import java.util.ArrayList;

public class Player {
    private Point2D playerVelocity;
    private boolean canJump;
    private Node playerNode;

    public Player(Node playerNode) {
        playerVelocity = new Point2D(0, 0);
        canJump = true;
        this.playerNode = playerNode;
    }

    public void jumpPlayer() {
        if (canJump) {
            playerVelocity = playerVelocity.add(0, -30);
            System.out.println("I JUMPED");
            canJump = false;
        }
    }

    public void movePlayerX(int value, ArrayList<Node> platforms) {
        boolean movingRight = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : platforms) {
                if (playerNode.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (playerNode.getTranslateX() + 40 == platform.getTranslateX()) {
                            return;
                        }
                    } else {
                        if (playerNode.getTranslateX() == platform.getTranslateX() + 60) {
                            return;
                        }
                    }
                }
            }
            playerNode.setTranslateX(playerNode.getTranslateX() + (movingRight ? 1 : -1));


        }
    }

    public void movePlayerY(int value, ArrayList<Node> platforms) {
        boolean movingDown = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : platforms) {
                if (playerNode.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (playerNode.getTranslateY() + 40 == platform.getTranslateY()) {
                            playerNode.setTranslateY(playerNode.getTranslateY() - 1);
                            canJump = true;
                            return;
                        }
                    } else {
                        if (playerNode.getTranslateY() == platform.getTranslateY() + 60) {
                            return;
                        }
                    }
                }
            }
            playerNode.setTranslateY(playerNode.getTranslateY() + (movingDown ? 1 : -1));
        }
    }

    public Node getPlayerNode() {
        return playerNode;
    }

    public Point2D getPlayerVelocity() {
        return playerVelocity;
    }

    public void setPlayerVelocity(Point2D playerVelocity) {
        this.playerVelocity = playerVelocity;
    }

}
