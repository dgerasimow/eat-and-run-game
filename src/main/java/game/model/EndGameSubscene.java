package game.model;

import com.google.gson.Gson;
import game.view.GameViewManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class EndGameSubscene extends SubScene {
    private GameButton exitButton;
    private Label winningLabel;
    private AnchorPane root;
    private GameViewManager gm = GameViewManager.getGm();


    public EndGameSubscene(String winner) {
        super(new AnchorPane(), 500, 500);
        prefWidth(500);
        prefHeight(500);

        root = (AnchorPane) this.getRoot();
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        initLabels(winner);
        initExitButton();
    }

    private void initLabels(String winner) {
        winningLabel = new GameLabel(winner + " won!", 30);
        winningLabel.setTranslateY(100);
        winningLabel.setTranslateX(75);
        root.getChildren().add(winningLabel);
    }

    private void initExitButton() {
        exitButton = new GameButton("Exit");
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Gson gson = new Gson();
                HashMap<String, String> message = new HashMap<>();
                message.put("method", "exit");
                gm.getClient().sendMessage(gson.toJson(message) + "\n");
                System.exit(1);
            }
        });
        exitButton.setTranslateX(75);
        exitButton.setTranslateY(200);
        root.getChildren().add(exitButton);
    }
}
