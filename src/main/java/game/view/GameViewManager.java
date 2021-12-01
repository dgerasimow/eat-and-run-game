package game.view;


import game.data.LevelData;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GameViewManager {

    private Scene gameScene;
    private Pane gamePane;
    private ArrayList<Node> platforms = new ArrayList<>();

    public GameViewManager() {

        Rectangle bottom = new Rectangle(1024, 40);
        bottom.setFill(Color.PALEVIOLETRED);
        AnchorPane.setBottomAnchor(bottom,15.0);

        Rectangle top = new Rectangle(1024, 40);
        top.setFill(Color.PALEVIOLETRED);
        AnchorPane.setTopAnchor(top,0.0);
        Rectangle left = new Rectangle(40, 800);
        left.setFill(Color.PALEVIOLETRED);
        AnchorPane.setLeftAnchor(left,0.0);
        Rectangle right = new Rectangle(40, 800);
        right.setFill(Color.PALEVIOLETRED);
        AnchorPane.setRightAnchor(right,0.0);

        gamePane = new AnchorPane(bottom, top, left, right);
        gameScene = new Scene(gamePane, 1024, 800);
        Image bg = new Image("background_main_menu.png", 1024,800, false, true);
        BackgroundImage background = new BackgroundImage(bg, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));
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
