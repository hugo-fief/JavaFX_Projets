package shortcutManagerFX;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import shortcutManagerFX.controller.DashBoardController;
import shortcutManagerFX.model.ShortcutManager;

/**
 * Classe principale de l'application Shortcut Manager.
 * Cette classe est responsable du lancement de l'application JavaFX,
 * de l'initialisation des services nécessaires, et du chargement de la vue principale.
 */
public class ShortcutMainExecutor extends Application {

    private ShortcutManager shortcutManager; // Gestionnaire des raccourcis

    /**
     * Méthode principale appelée au démarrage de l'application JavaFX.
     * Elle configure et affiche la fenêtre principale.
     *
     * @param primaryStage La fenêtre principale de l'application JavaFX.
     */
    @Override
    public void start(Stage primaryStage) {
        // Initialisation du gestionnaire des raccourcis et du service
        shortcutManager = new ShortcutManager();

        try {
            // Charger le fichier FXML pour la vue principale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/shortcutManagerFX/DashBoard.fxml"));
            AnchorPane root = loader.load(); // Conteneur principal de la vue (AnchorPane)
            
            // Configurer et afficher la scène principale
            Scene mainScene = new Scene(root);
            primaryStage.setTitle("Interface de Gestion des Raccourcis"); // Définir le titre de la fenêtre
            primaryStage.setScene(mainScene); // Attacher la scène à la fenêtre principale
            
            // Récupérer le contrôleur associé au fichier FXML
            DashBoardController dashBoardController = loader.getController();
            
            // Initialiser le contrôleur avec les dépendances et la scène principale
            dashBoardController.initialize(shortcutManager);
            
            primaryStage.show(); // Afficher la fenêtre principale
        } catch (IOException e) {
            // Gérer les erreurs lors du chargement du fichier FXML
            e.printStackTrace();
        }
    }

    /**
     * Méthode principale pour lancer l'application.
     *
     * @param args Les arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        launch(args); // Lance l'application JavaFX
    }
}
