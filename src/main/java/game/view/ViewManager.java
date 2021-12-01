package game.view;

import game.model.GameButton;
import game.model.GameSubscene;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ViewManager {
    public static final int HEIGHT = 800;
    public static final int WIDTH = 1024;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    List<GameButton> menuButtons;

    private GameSubscene infoSubScene;

    public ViewManager() {
        this.mainPane = new AnchorPane();
        this.mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        this.mainStage = new Stage();
        mainStage.setScene(mainScene);
        menuButtons = new ArrayList<>();
        //TODO: needs to be researched
//        createSubScenes();
        createButtons();
        createBackground();
//        когда сделаю лого
        createLogo();


    }
    private void createSubScenes() {
        infoSubScene = new GameSubscene();
        mainPane.getChildren().add(infoSubScene);
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void createButtons() {
        createNewGameButton();
        createConnectToGameButton();
        createInfoButton();
        createCreditsButton();
        createExitButton();
    }

    private void createNewGameButton() {
        GameButton newGameButton = new GameButton("NEW GAME");
        addMenuButton(newGameButton);
        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GameViewManager gameViewManager = new GameViewManager();
                gameViewManager.createGame(mainStage);
            }
        });
    }

    private void createConnectToGameButton() {
        GameButton connectToGameButton = new GameButton("CONNECT");
        addMenuButton(connectToGameButton);
    }

    private void createInfoButton() {
            GameButton infoButton = new GameButton("INFO");
            addMenuButton(infoButton);
//TODO: needs to be researched

//            infoButton.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent actionEvent) {
//                    infoSubScene.moveSubScene();
//                }
//            });
    }

    private void createCreditsButton() {
        GameButton creditsButton = new GameButton("CREDITS");
        addMenuButton(creditsButton);
    }

    private void createExitButton() {
        GameButton exitButton = new GameButton("EXIT");
        addMenuButton(exitButton);
    }


    private void addMenuButton(GameButton button) {
        button.setLayoutX(450);
        button.setLayoutY(400 + (menuButtons.size() * 80));
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createBackground() {
        Image backgroundImage = new Image("background_main_menu.png", 1024, 800, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));

    }
    
    public void createLogo() {
        ImageView logo = new ImageView("game_logo.png");
        logo.setLayoutX(200);
        logo.setLayoutY(0);
        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(new DropShadow());
            }
        });
        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(null);
            }
        });
        mainPane.getChildren().add(logo);
    }
}
