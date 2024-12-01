package shortcutManagerFX.controller;

import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import shortcutManagerFX.model.ShortcutManager;
import shortcutManagerFX.util.ShortcutValidator;

/**
 * Contrôleur pour la configuration des raccourcis.
 * Gère l'affichage, la modification et la validation des raccourcis de manière dynamique.
 */
public class ShortcutConfigController {

    @FXML
    private VBox shortcutContainer; // Conteneur dynamique pour les raccourcis

    private ShortcutManager shortcutManager; // Gestionnaire des raccourcis
    private final Map<String, TextField> shortcutFields = new HashMap<>(); // Champs dynamiques pour chaque raccourci
    private Runnable onShortcutChangeListener; // Action à exécuter lorsqu'un raccourci est modifié

    /**
     * Initialise le contrôleur avec le gestionnaire des raccourcis.
     *
     * @param shortcutManager Le gestionnaire des raccourcis.
     */
    public void initialize(ShortcutManager shortcutManager) {
        this.shortcutManager = shortcutManager;

        // Créer dynamiquement les champs de texte et les boutons pour chaque raccourci
        shortcutManager.getAllShortcuts().forEach((action, shortcut) -> {
            HBox shortcutRow = new HBox(10); // Conteneur pour chaque ligne
            TextField shortcutField = new TextField(shortcut); // Champ de texte pour le raccourci
            shortcutFields.put(action, shortcutField); // Associer le champ à l'action

            // Bouton pour vider le champ
            Button clearButton = new Button("X");
            clearButton.setOnAction(event -> shortcutField.clear());

            // Configurer le champ pour gérer les raccourcis
            initializeShortcutField(shortcutField);

            // Ajouter les éléments à la ligne
            shortcutRow.getChildren().addAll(shortcutField, clearButton);

            // Ajouter la ligne au conteneur principal
            shortcutContainer.getChildren().add(shortcutRow);
        });
    }

    /**
     * Configure un champ de texte pour gérer les raccourcis avec :
     * - Ajout automatique de "Ctrl" si le champ est vide.
     * - Concaténation correcte des touches.
     * - Suppression intuitive avec "Backspace" ou "Delete".
     *
     * @param shortcutField Le champ de texte à configurer.
     */
    private void initializeShortcutField(TextField shortcutField) {
        shortcutField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            String currentText = shortcutField.getText(); // Texte actuel du champ
            KeyCode keyCode = event.getCode(); // Code de la touche pressée

            // Gestion des touches spéciales
            if (keyCode == KeyCode.BACK_SPACE || keyCode == KeyCode.DELETE) {
                int lastPlusIndex = currentText.lastIndexOf("+");
                if (lastPlusIndex != -1) {
                    shortcutField.setText(currentText.substring(0, lastPlusIndex));
                } else {
                    shortcutField.clear();
                }
                event.consume();
            } else if (keyCode == KeyCode.CONTROL) {
                if (currentText.isEmpty()) {
                    shortcutField.setText("Ctrl");
                }
                event.consume();
            } else if (keyCode.isArrowKey()) {
                    // Ajouter les flèches directionnelles
                    String keyText = keyCode.getName();
                    if (currentText.startsWith("Ctrl")) {
                        shortcutField.setText(currentText + "+" + keyText);
                    } else {
                        shortcutField.setText("Ctrl+" + keyText);
                    }
                    event.consume();
            } else if (!currentText.isEmpty() && currentText.startsWith("Ctrl")) {
                String keyText = keyCode.getName().toUpperCase(); // Nom de la touche en majuscule
                if (!keyText.equals("CTRL")) {
                    shortcutField.setText(currentText + "+" + keyText);
                    event.consume();
                }
            } else {
                event.consume();
            }
        });
    }

    /**
     * Sauvegarde les modifications apportées aux raccourcis.
     * Vérifie que tous les raccourcis sont valides et uniques.
     */
    @FXML
    private void saveChanges() {
        for (Map.Entry<String, TextField> entry : shortcutFields.entrySet()) {
            String action = entry.getKey();
            String shortcut = entry.getValue().getText();

            // Valider le raccourci
            if (!ShortcutValidator.isValidShortcut(shortcut)) {
                showErrorAlert("Invalid Shortcut", "The shortcut for " + action + " is invalid.");
                return;
            }

            // Vérifier les doublons
            if (shortcutManager.isShortcutDuplicate(shortcut, action)) {
                showErrorAlert("Duplicate Shortcut", "The shortcut '" + shortcut + "' is already in use.");
                return;
            }

            // Mettre à jour le raccourci dans le gestionnaire
            shortcutManager.setShortcut(action, shortcut);
        }

        // Sauvegarder les raccourcis
        shortcutManager.saveShortcuts();
        notifyShortcutChange(); // Notifier après sauvegarde réussie
        System.out.println("Raccourcis sauvegardés avec succès !");
    }

    /**
     * Affiche une boîte de dialogue d'erreur avec un message spécifique.
     *
     * @param title   Le titre de l'erreur.
     * @param message Le message de l'erreur.
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Affiche une boîte de dialogue de succès avec un message spécifique.
     *
     * @param title   Le titre de la boîte de dialogue.
     * @param message Le message à afficher.
     */
    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Définit une action à exécuter lorsque les raccourcis sont modifiés.
     *
     * @param listener Action à exécuter.
     */
    public void setOnShortcutChangeListener(Runnable listener) {
        this.onShortcutChangeListener = listener;
    }

    /**
     * Notifie la fenêtre parente si les raccourcis sont modifiés.
     * À appeler dans la méthode saveChanges après validation et sauvegarde.
     */
    private void notifyShortcutChange() {
        if (onShortcutChangeListener != null) {
            onShortcutChangeListener.run();
        }
        
        // Afficher une pop-in de succès
       showSuccessAlert("Changes Saved", "All shortcuts have been successfully updated!");
    }
}
