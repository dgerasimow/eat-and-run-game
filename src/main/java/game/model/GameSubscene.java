package game.model;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class GameSubscene extends SubScene {

    public static final String FONT_PATH = "src/main/java/game/model/resources/fonts/chalkboy.otf";
    public static final String BACKGROUND_IMAGE = "main_menu_subscene.png";


    public GameSubscene() {
        super(new AnchorPane(), 800, 800);
        prefWidth(455);
        prefHeight(298);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 455, 298,
                false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        root2.setBackground(new Background(image));

        setLayoutX(1024);
        setLayoutY(180);
    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        transition.setToX(-676);

        transition.play();
    }

}
