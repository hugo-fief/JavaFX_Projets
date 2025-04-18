package shortcutManagerFX.lastVersion;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ShortcutHelpWindow {

    // Singleton pour éviter d’ouvrir plusieurs fois la même fenêtre
    private static Stage activeStage = null;

    /**
     * Méthode à appeler quand l'utilisateur fait "Ctrl + H"
     */
    public static void show() {
        // Si la fenêtre est déjà affichée, ne rien faire
        if (activeStage != null && activeStage.isShowing()) {
            activeStage.toFront();  // la ramener au premier plan
            return;
        }

        try {
            // Chargement du fichier FXML
            FXMLLoader loader = new FXMLLoader(ShortcutHelpWindow.class.getResource("ShortcutHelpWindow.fxml"));
            Parent root = loader.load();

            // Création de la scène et de la fenêtre
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Raccourcis clavier - TrapilAnalyse");
            stage.setScene(scene);
            stage.initModality(Modality.NONE); // Laisse les autres fenêtres utilisables
            stage.setAlwaysOnTop(true);        // Toujours au premier plan
            stage.setResizable(false);

            // Associe la fenêtre statique pour le singleton
            activeStage = stage;

            // Fermeture rapide via la touche Échap
            scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                }
            });

            // Affiche la fenêtre
            stage.show();

            // Animation Fade-In à l'ouverture (apparition douce)
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

            // Nettoyage de l’instance quand on ferme la fenêtre
            stage.setOnHidden(e -> activeStage = null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


