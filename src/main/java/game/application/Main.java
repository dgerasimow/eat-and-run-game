package game.application;

import game.view.ViewManager;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();

    private ArrayList<Node> platforms = new ArrayList<>();

    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();

    private Node player;
    private Point2D playerVelocity = new Point2D(0,0);
    private boolean canJump = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewManager vm = new ViewManager();
        initContent();

        Scene scene = new Scene(Parent root);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        primaryStage.setTitle("Eat&Run");
        primaryStage.setScene(vm.);
        primaryStage.show();


    }

    private void initContent() {
    }

    public static void main(String[] args) {
        launch(args);
    }
}
