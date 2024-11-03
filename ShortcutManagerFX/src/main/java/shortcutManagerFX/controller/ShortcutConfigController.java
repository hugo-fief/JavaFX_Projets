package shortcutManagerFX.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import shortcutManagerFX.model.ShortcutManager;
import shortcutManagerFX.util.ShortcutValidator;

public class ShortcutConfigController {
    private ShortcutManager shortcutManager;
    private Stage mainStage;

    @FXML
    private TextField saveField;
    @FXML
    private TextField openField;

    public void initialize(ShortcutManager shortcutManager, Stage mainStage) {
        this.shortcutManager = shortcutManager;
        this.mainStage = mainStage;
        saveField.setText(shortcutManager.getShortcut("save"));
        openField.setText(shortcutManager.getShortcut("open"));
    }

    @FXML
    private void saveChanges() {
        String saveShortcut = saveField.getText();
        String openShortcut = openField.getText();

        if (!ShortcutValidator.isValidShortcut(saveShortcut)) {
            showErrorAlert("Invalid Save Shortcut", "The save shortcut you entered is invalid.");
            return;
        }

        if (!ShortcutValidator.isValidShortcut(openShortcut)) {
            showErrorAlert("Invalid Open Shortcut", "The open shortcut you entered is invalid.");
            return;
        }

        shortcutManager.setShortcut("save", saveShortcut);
        shortcutManager.setShortcut("open", openShortcut);
        shortcutManager.saveShortcuts();

        // Retourner à la scène principale après avoir sauvegardé
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/shortcutManagerFX/DashBoard.fxml"));
        try {
            Scene mainScene = new Scene(loader.load());
            DashBoardController dashBoardController = loader.getController();
            dashBoardController.initialize(shortcutManager);
            mainStage.setScene(mainScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
