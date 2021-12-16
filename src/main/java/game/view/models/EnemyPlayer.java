package game.view.models;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class EnemyPlayer extends Rectangle {
    private Node playerNode;
    private String name;

    public EnemyPlayer(Node playerNode, String name) {
        this.playerNode = playerNode;
        this.name = name;
    }

    public synchronized void moveEnemy(double x, double y) {
        setX(x);
        setY(y);
    }

    public Node getPlayerNode() {
        return playerNode;
    }
}
