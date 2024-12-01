package shortcutManagerFX;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import shortcutManagerFX.controller.DashBoardController;
import shortcutManagerFX.model.ShortcutManager;

public class ShortcutMainExecutor extends Application {

    private ShortcutManager shortcutManager;
    private ShortcutManagerService shortcutManagerService;

    @Override
    public void start(Stage primaryStage) {
        shortcutManager = new ShortcutManager();
        shortcutManagerService = new ShortcutManagerService();

        try {
            // Charger le fichier FXML principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/shortcutManagerFX/DashBoard.fxml"));
            AnchorPane root = loader.load();

            // Récupérer le contrôleur et l'initialiser
            DashBoardController dashBoardController = loader.getController();
            dashBoardController.initialize(shortcutManager, shortcutManagerService, new Scene(root));

            // Configurer la scène et la fenêtre principale
            Scene mainScene = new Scene(root);
            primaryStage.setTitle("Shortcut Manager");
            primaryStage.setScene(mainScene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args); // Démarrer l'application JavaFX
    }
}
