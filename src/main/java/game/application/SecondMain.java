package game.application;

import game.view.ViewManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

public class SecondMain extends Application {

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();

    private Pane appRoot = new Pane();

    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewManager vm = new ViewManager(primaryStage);

        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Eat&Run");
        primaryStage.setScene(vm.getMenuScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
