package shortcutManagerFX.controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import shortcutManagerFX.model.ShortcutManager;

public class ShortcutConfigController {
    private ShortcutManager shortcutManager;

    public ShortcutConfigController(ShortcutManager shortcutManager) {
        this.shortcutManager = shortcutManager;
    }

    public void showConfigWindow() {
        Stage configStage = new Stage();
        configStage.setTitle("Configure Shortcuts");

        GridPane grid = new GridPane();
        Label saveLabel = new Label("Save Shortcut:");
        TextField saveField = new TextField(shortcutManager.getShortcut("save"));
        Label openLabel = new Label("Open Shortcut:");
        TextField openField = new TextField(shortcutManager.getShortcut("open"));
        Label exitLabel = new Label("Exit Shortcut:");
        TextField exitField = new TextField(shortcutManager.getShortcut("exit"));

        Button saveButton = new Button("Save Changes");
        saveButton.setOnAction(e -> {
            shortcutManager.setShortcut("save", saveField.getText());
            shortcutManager.setShortcut("open", openField.getText());
            shortcutManager.setShortcut("exit", exitField.getText());
            shortcutManager.saveShortcuts();
            configStage.close();
        });

        grid.addRow(0, saveLabel, saveField);
        grid.addRow(1, openLabel, openField);
        grid.addRow(2, exitLabel, exitField);
        grid.addRow(3, saveButton);

        configStage.setScene(new Scene(grid, 300, 200));
        configStage.show();
    }
}

