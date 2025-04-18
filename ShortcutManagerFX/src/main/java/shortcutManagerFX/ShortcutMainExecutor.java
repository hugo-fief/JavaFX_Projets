package shortcutManagerFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import shortcutManagerFX.lastVersion.ShortcutHelpManager;

public class ShortcutMainExecutor extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 400, 200);

        // Installation du raccourci Ctrl + H
        ShortcutHelpManager.install(scene);

        primaryStage.setTitle("Demo raccourcis - Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    /*public void start(Stage primaryStage) {
        shortcutManager = new ShortcutManager();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/shortcutManagerFX/DashBoard.fxml"));
            AnchorPane root = loader.load(); // Conteneur principal de la vue (AnchorPane)
            
            Scene mainScene = new Scene(root);
            primaryStage.setTitle("Interface de Gestion des Raccourcis"); // Définir le titre de la fenêtre
            primaryStage.setScene(mainScene); // Attacher la scène à la fenêtre principale
            
            DashBoardController dashBoardController = loader.getController();
            
            dashBoardController.initialize(shortcutManager);
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
