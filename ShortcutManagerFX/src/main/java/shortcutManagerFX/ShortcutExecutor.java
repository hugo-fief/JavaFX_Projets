package shortcutManagerFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import shortcutManagerFX.controller.ShortcutConfigController;
import shortcutManagerFX.model.ShortcutManager;

public class ShortcutExecutor extends Application {
    private ShortcutManager shortcutManager;

    @Override
    public void start(Stage primaryStage) {
        shortcutManager = new ShortcutManager();

        Button saveButton = new Button("Save (Shortcut: " + shortcutManager.getShortcut("save") + ")");
        saveButton.setOnAction(e -> System.out.println("Save action triggered!"));

        Button openButton = new Button("Open (Shortcut: " + shortcutManager.getShortcut("open") + ")");
        openButton.setOnAction(e -> System.out.println("Open action triggered!"));

        Button configButton = new Button("Configure Shortcuts");
        configButton.setOnAction(e -> new ShortcutConfigController(shortcutManager).showConfigWindow());

        VBox layout = new VBox(10, saveButton, openButton, configButton);
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Shortcut App");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
