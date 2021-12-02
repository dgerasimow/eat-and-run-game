package game.model;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameLabel extends Label {
    public static final String FONT_PATH = "src/main/java/game/model/resources/fonts/chalkboy.otf";

    public GameLabel(String text, int fontSize) {
        super(text);
        setLabelFont(fontSize);
    }

    private void setLabelFont(int size) {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), size));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", size));
        }
    }
}
