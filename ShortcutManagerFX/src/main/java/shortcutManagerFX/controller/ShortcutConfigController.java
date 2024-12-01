package shortcutManagerFX.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import shortcutManagerFX.model.ShortcutManager;
import shortcutManagerFX.util.ShortcutValidator;

public class ShortcutConfigController {

    @FXML
    private TextField saveField;

    @FXML
    private TextField openField;

    @FXML
    private Button clearSaveButton;

    @FXML
    private Button clearOpenButton;

    private ShortcutManager shortcutManager;

    /**
     * Initialise le contrôleur avec le gestionnaire de raccourcis.
     * Configure les champs de saisie et ajoute les gestionnaires nécessaires.
     *
     * @param shortcutManager Le gestionnaire des raccourcis.
     */
    public void initialize(ShortcutManager shortcutManager) {
        this.shortcutManager = shortcutManager;

        // Initialiser les champs avec les raccourcis existants
        saveField.setText(shortcutManager.getShortcut("action1"));
        openField.setText(shortcutManager.getShortcut("action2"));

        // Ajouter des gestionnaires pour les champs
        initializeShortcutField(saveField);
        initializeShortcutField(openField);
    }

    /**
     * Configure un champ de texte pour gérer les raccourcis avec :
     * - La suppression du dernier segment (touche "Del").
     * - L'ajout automatique de "+" et la conversion en majuscule.
     * - Une validation stricte pour commencer par "Ctrl".
     *
     * @param shortcutField Le champ de texte à configurer.
     */
    private void initializeShortcutField(TextField shortcutField) {
        shortcutField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            String currentText = shortcutField.getText();
            String keyText = event.getText().toUpperCase(); // Convertir en majuscule

            // Gestion des touches spéciales
            if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
                // Supprimer le dernier segment (jusqu'au dernier "+")
                int lastPlusIndex = currentText.lastIndexOf("+");
                if (lastPlusIndex != -1) {
                    shortcutField.setText(currentText.substring(0, lastPlusIndex));
                } else {
                    shortcutField.clear();
                }
                event.consume();
            } else if (keyText.equals("CTRL")) {
                // Toujours commencer par "Ctrl" si le champ est vide
                if (currentText.isEmpty()) {
                    shortcutField.setText("Ctrl");
                }
                event.consume();
            } else if (!currentText.isEmpty()) {
                // Ajouter la touche saisie avec un "+" (si Ctrl est présent)
                shortcutField.setText(currentText + "+" + keyText);
                event.consume();
            } else {
                // Refuser la saisie si le champ est vide (Ctrl obligatoire)
                event.consume();
            }
        });
    }

    /**
     * Gère l'événement de clic pour vider un champ de saisie.
     * Ce gestionnaire est lié au bouton "Clear" dans le FXML.
     */
    @FXML
    private void clearSaveField() {
        saveField.clear();
    }

    @FXML
    private void clearOpenField() {
        openField.clear();
    }

    /**
     * Valide et sauvegarde les raccourcis saisis par l'utilisateur.
     * Affiche une erreur si les raccourcis sont invalides.
     */
    @FXML
    private void saveChanges() {
        String saveShortcut = saveField.getText();
        String openShortcut = openField.getText();

        // Validation des raccourcis
        if (!ShortcutValidator.isValidShortcut(saveShortcut)) {
            showErrorAlert("Invalid Save Shortcut", ShortcutValidator.getInvalidShortcutMessage());
            return;
        }
        if (!ShortcutValidator.isValidShortcut(openShortcut)) {
            showErrorAlert("Invalid Open Shortcut", ShortcutValidator.getInvalidShortcutMessage());
            return;
        }

        // Mettre à jour et sauvegarder les raccourcis
        shortcutManager.setShortcut("action1", saveShortcut);
        shortcutManager.setShortcut("action2", openShortcut);
        shortcutManager.saveShortcuts();

        System.out.println("Raccourcis mis à jour avec succès !");
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
}
