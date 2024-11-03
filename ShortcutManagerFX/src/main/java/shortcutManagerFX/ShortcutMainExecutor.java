package shortcutManagerFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import shortcutManagerFX.controller.ShortcutConfigController;
import shortcutManagerFX.model.ShortcutManager;

public class ShortcutMainExecutor extends Application {
	private ShortcutManager shortcutManager;
    private Button saveButton;
    private Button openButton;

    @Override
    public void start(Stage primaryStage) {
        shortcutManager = new ShortcutManager();

        saveButton = new Button();
        openButton = new Button();
        refreshShortcuts();

        Button configButton = new Button("Configure Shortcuts");
        configButton.setOnAction(e -> {
            // Ouvrir la fenêtre de configuration et passer l'instance de MainApp pour rafraîchir les raccourcis
            new ShortcutConfigController(shortcutManager, this).showConfigWindow();
        });

        VBox layout = new VBox(10, saveButton, openButton, configButton);
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Shortcut App");
        primaryStage.show();
    }

    public void refreshShortcuts() {
        // Actualiser les boutons avec les nouveaux raccourcis
        saveButton.setText("Save (Shortcut: " + shortcutManager.getShortcut("save") + ")");
        openButton.setText("Open (Shortcut: " + shortcutManager.getShortcut("open") + ")");
    }

    public static void main(String[] args) {
        launch(args);
    }
}