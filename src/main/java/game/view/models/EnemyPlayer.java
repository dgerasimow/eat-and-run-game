package game.view.models;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class EnemyPlayer extends Rectangle {
    private final Node playerNode;

    public EnemyPlayer(Node playerNode) {
        this.playerNode = playerNode;
    }

    public Node getPlayerNode() {
        return playerNode;
    }
}
