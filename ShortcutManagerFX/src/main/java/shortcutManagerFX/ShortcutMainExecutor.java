package shortcutManagerFX;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import shortcutManagerFX.controller.DashBoardController;
import shortcutManagerFX.model.ShortcutManager;

public class ShortcutMainExecutor extends Application {
    private ShortcutManager shortcutManager = new ShortcutManager();

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/shortcutManagerFX/DashBoard.fxml"));
            Scene mainScene = new Scene(loader.load());
            
            File cssFile = new File("src/main/resources/shortcutManagerFX/styles.css");
            mainScene.getStylesheets().add(cssFile.toURI().toString());

            DashBoardController mainController = loader.getController();
            mainController.initialize(shortcutManager);

            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Shortcut Manager");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
