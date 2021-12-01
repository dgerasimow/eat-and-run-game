package game.view;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameViewManager {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
//    private Player player;

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 800;

    public GameViewManager() {
        initializeStage();
    }

    private void createKeyListners() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

            }
        });
//        gameScene.setOnKeyReleased();
    }

    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);


    }

    public void createGame(Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();
    }


}
