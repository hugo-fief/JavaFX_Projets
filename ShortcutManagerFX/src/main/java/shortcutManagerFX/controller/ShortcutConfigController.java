package shortcutManagerFX.controller;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import shortcutManagerFX.ShortcutMainExecutor;
import shortcutManagerFX.model.ShortcutManager;

public class ShortcutConfigController {
	private ShortcutManager shortcutManager;
	private ShortcutMainExecutor shortcutMainExecutor;

	public ShortcutConfigController(ShortcutManager shortcutManager, ShortcutMainExecutor shortcutMainExecutor) {
		this.shortcutManager = shortcutManager;
		this.shortcutMainExecutor = shortcutMainExecutor;
	}

	public void showConfigWindow() {
		Stage configStage = new Stage();
		configStage.setTitle("Configure Shortcuts");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setStyle("-fx-padding: 20;");

		Label saveLabel = new Label("Save Shortcut:");
		TextField saveField = new TextField(shortcutManager.getShortcut("save"));
		Label openLabel = new Label("Open Shortcut:");
		TextField openField = new TextField(shortcutManager.getShortcut("open"));

		Button saveButton = new Button("Save Changes");
		saveButton.setOnAction(e -> {
			shortcutManager.setShortcut("save", saveField.getText());
			shortcutManager.setShortcut("open", openField.getText());
			shortcutManager.saveShortcuts();
			shortcutMainExecutor.refreshShortcuts();
			configStage.close();
		});

		grid.addRow(0, saveLabel, saveField);
		grid.addRow(1, openLabel, openField);
		grid.addRow(2, saveButton);

		Scene scene = new Scene(grid, 300, 200);

		// Appliquer le fichier CSS
		File cssFile = new File("src/main/resources/shortcutManagerFX/styles.css");
		scene.getStylesheets().add(cssFile.toURI().toString());

		configStage.setScene(scene);
		configStage.show();
	}
}