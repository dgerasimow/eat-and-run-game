package game.model;

import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class EndGameSubscene extends SubScene {
    private static final String FONT_PATH = "src/main/java/game/model/resources/fonts/chalkboy.otf";
    private GameButton restartButton;
    private GameButton mainMenuButton;


    public EndGameSubscene() {
        super(new AnchorPane(), 500, 500);
        prefWidth(500);
        prefHeight(500);

        AnchorPane thisRoot = (AnchorPane) this.getRoot();
        thisRoot.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));

    }
}
