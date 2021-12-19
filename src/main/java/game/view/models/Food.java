package game.view.models;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class Food {
    private int id;
    private Node foodNode;

    public Food(int id, Node foodNode) {
        this.id = id;
        this.foodNode = foodNode;
    }


    public int getFoodId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Node getFoodNode() {
        return foodNode;
    }

}
