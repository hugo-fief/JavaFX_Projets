package shortcutManagerFX.controller;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import shortcutManagerFX.ShortcutMainExecutor;
import shortcutManagerFX.model.ShortcutManager;
import shortcutManagerFX.util.ShortcutValidator;

public class ShortcutConfigController {
	private ShortcutManager shortcutManager;
	private ShortcutMainExecutor shortcutMainExecutor;

	public ShortcutConfigController(ShortcutManager shortcutManager, ShortcutMainExecutor shortcutMainExecutor) {
		this.shortcutManager = shortcutManager;
		this.shortcutMainExecutor = shortcutMainExecutor;
	}

	public void showConfigWindow(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-padding: 20;");

        Label saveLabel = new Label("Save Shortcut:");
        TextField saveField = new TextField(shortcutManager.getShortcut("save"));
        Label openLabel = new Label("Open Shortcut:");
        TextField openField = new TextField(shortcutManager.getShortcut("open"));

        Button saveButton = new Button("Save Changes");
        saveButton.setPrefWidth(150);  // Définir une largeur pour le bouton
        
        saveButton.setOnAction(e -> {
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
            shortcutMainExecutor.refreshShortcuts();
            primaryStage.setScene(shortcutMainExecutor.getMainScene());  // Retourner à la scène principale
        });

        grid.addRow(0, saveLabel, saveField);
        grid.addRow(1, openLabel, openField);
        grid.addRow(2, saveButton);

        Scene configScene = new Scene(grid, 300, 200);
        File cssFile = new File("src/main/resources/shortcutManagerFX/styles.css");
        configScene.getStylesheets().add(cssFile.toURI().toString());

        primaryStage.setScene(configScene);  // Remplacer la scène principale par la scène de configuration
    }
	
	private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}