package shortcutManagerFX.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
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

        // Configurer les raccourcis clavier
        configureKeyboardShortcuts(saveButton.getScene());
    }

    @FXML
    private void performSaveAction() {
        System.out.println("Save action triggered!");
    }

    @FXML
    private void performOpenAction() {
        System.out.println("Open action triggered!");
    }

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

    private void configureKeyboardShortcuts(Scene scene) {
        // Ajouter un écouteur pour les raccourcis clavier
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (isShortcutPressed(event, shortcutManager.getShortcut("save"))) {
                performSaveAction();
            } else if (isShortcutPressed(event, shortcutManager.getShortcut("open"))) {
                performOpenAction();
            } else if (isShortcutPressed(event, shortcutManager.getShortcut("config"))) {
                openConfigWindow();
            }
        });
    }

    private boolean isShortcutPressed(KeyEvent event, String shortcut) {
        KeyCombination keyCombination = KeyCombination.keyCombination(shortcut.replace("Maj", "Shift"));
        return keyCombination.match(event);
    }
}
