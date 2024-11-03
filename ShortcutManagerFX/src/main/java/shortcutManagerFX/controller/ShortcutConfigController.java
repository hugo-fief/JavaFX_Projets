package shortcutManagerFX.controller;

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
        Label saveLabel = new Label("Save Shortcut:");
        TextField saveField = new TextField(shortcutManager.getShortcut("save"));
        Label openLabel = new Label("Open Shortcut:");
        TextField openField = new TextField(shortcutManager.getShortcut("open"));

        Button saveButton = new Button("Save Changes");
        saveButton.setOnAction(e -> {
            shortcutManager.setShortcut("save", saveField.getText());
            shortcutManager.setShortcut("open", openField.getText());
            shortcutManager.saveShortcuts();

            // Appeler refreshShortcuts() pour rafraîchir la fenêtre principale
            shortcutMainExecutor.refreshShortcuts();
            configStage.close();
        });

        grid.addRow(0, saveLabel, saveField);
        grid.addRow(1, openLabel, openField);
        grid.addRow(2, saveButton);

        configStage.setScene(new Scene(grid, 300, 200));
        configStage.show();
    }
}
