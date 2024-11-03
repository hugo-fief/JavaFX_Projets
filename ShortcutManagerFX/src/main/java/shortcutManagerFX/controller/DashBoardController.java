package shortcutManagerFX.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import shortcutManagerFX.model.ShortcutManager;

public class DashBoardController {
    private ShortcutManager shortcutManager;

    @FXML
    private Button saveButton;
    @FXML
    private Button openButton;

    public void initialize(ShortcutManager shortcutManager) {
        this.shortcutManager = shortcutManager;
        refreshShortcuts();
    }

    @FXML
    private void performSaveAction() {
        System.out.println("Save action triggered!");
    }

    @FXML
    private void performOpenAction() {
        System.out.println("Open action triggered!");
    }

    /*private void openConfigWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        new ShortcutConfigController().openConfigWindow(stage, shortcutManager);
    }*/
    
    @FXML
    private void openConfigWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/shortcutManagerFX/ShortcutConfig.fxml"));
            Scene configScene = new Scene(loader.load());

            // Initialiser le contrôleur de configuration avec le gestionnaire de raccourcis et la fenêtre principale
            ShortcutConfigController configController = loader.getController();
            configController.initialize(shortcutManager, (Stage) saveButton.getScene().getWindow());

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.setScene(configScene);  // Remplacer la scène principale par celle de configuration
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshShortcuts() {
        saveButton.setText("Save (Shortcut: " + shortcutManager.getShortcut("save") + ")");
        openButton.setText("Open (Shortcut: " + shortcutManager.getShortcut("open") + ")");
    }
}
