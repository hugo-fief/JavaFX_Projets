package shortcutManagerFX.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import shortcutManagerFX.model.ShortcutManager;
import shortcutManagerFX.util.ShortcutValidator;

public class ShortcutConfigController {

    @FXML
    private TextField saveField;

    @FXML
    private TextField openField;

    private ShortcutManager shortcutManager;

    /**
     * Initialise le contrôleur avec le gestionnaire de raccourcis.
     *
     * @param shortcutManager Le gestionnaire des raccourcis.
     */
    public void initialize(ShortcutManager shortcutManager) {
        this.shortcutManager = shortcutManager;

        // Initialiser les champs avec les raccourcis existants
        saveField.setText(shortcutManager.getShortcut("action1"));
        openField.setText(shortcutManager.getShortcut("action2"));
    }

    /**
     * Méthode générique pour vider un champ de texte, basée sur l'identifiant de l'input.
     *
     * @param event L'événement déclenché par le bouton.
     */
    @FXML
    private void clearField(MouseEvent event) {
        // Récupérer le bouton qui a déclenché l'action
        Node source = (Node) event.getSource();

        // Lire le userData pour savoir quel input est concerné
        String inputId = (String) source.getUserData();

        // Trouver et vider le champ correspondant
        switch (inputId) {
            case "saveField":
                saveField.clear();
                break;
            case "openField":
                openField.clear();
                break;
            default:
                System.err.println("Aucun champ correspondant pour l'identifiant : " + inputId);
                break;
        }
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
