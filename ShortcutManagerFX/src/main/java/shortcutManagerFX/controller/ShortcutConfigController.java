package shortcutManagerFX.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import shortcutManagerFX.model.ShortcutManager;
import shortcutManagerFX.util.ShortcutValidator;

public class ShortcutConfigController {

    @FXML
    private TextField saveField;

    @FXML
    private TextField openField;

    private ShortcutManager shortcutManager;

    public void initialize(ShortcutManager shortcutManager) {
        this.shortcutManager = shortcutManager;
        saveField.setText(shortcutManager.getShortcut("action1"));
        openField.setText(shortcutManager.getShortcut("action2"));
    }

    @FXML
    private void saveChanges() {
        String saveShortcut = saveField.getText();
        String openShortcut = openField.getText();

        if (!ShortcutValidator.isValidShortcut(saveShortcut)) {
            showErrorAlert("Invalid Save Shortcut", "Le raccourci pour l'action 'Save' est invalide.");
            return;
        }
        if (!ShortcutValidator.isValidShortcut(openShortcut)) {
            showErrorAlert("Invalid Open Shortcut", "Le raccourci pour l'action 'Open' est invalide.");
            return;
        }

        shortcutManager.setShortcut("action1", saveShortcut);
        shortcutManager.setShortcut("action2", openShortcut);
        shortcutManager.saveShortcuts();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
