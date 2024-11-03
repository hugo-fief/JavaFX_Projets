package shortcutManagerFX;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import shortcutManagerFX.controller.ShortcutConfigController;
import shortcutManagerFX.model.ShortcutManager;

public class ShortcutMainExecutor extends Application {
    private ShortcutManager shortcutManager;
    private Button saveButton;
    private Button openButton;
    private Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        shortcutManager = new ShortcutManager();

        saveButton = new Button("Save (Shortcut: " + shortcutManager.getShortcut("save") + ")");
        saveButton.setOnAction(e -> performSaveAction());

        openButton = new Button("Open (Shortcut: " + shortcutManager.getShortcut("open") + ")");
        openButton.setOnAction(e -> performOpenAction());

        Button configButton = new Button("Configure Shortcuts");
        configButton.setOnAction(e -> performOpenConfigAction(primaryStage));

        VBox layout = new VBox(10, saveButton, openButton, configButton);
        layout.setSpacing(15);
        mainScene = new Scene(layout, 300, 200);  // Initialiser mainScene

        File cssFile = new File("src/main/resources/shortcutManagerFX/styles.css");
        mainScene.getStylesheets().add(cssFile.toURI().toString());

        configureKeyboardShortcuts(mainScene);

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Shortcut App");
        primaryStage.show();
    }

    private void configureKeyboardShortcuts(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            String saveShortcut = shortcutManager.getShortcut("save");
            String openShortcut = shortcutManager.getShortcut("open");
            String configShortcut = shortcutManager.getShortcut("config");

            if (isShortcutPressed(event, saveShortcut)) {
                performSaveAction();
            } else if (isShortcutPressed(event, openShortcut)) {
                performOpenAction();
            } else if (isShortcutPressed(event, configShortcut)) {
                performOpenConfigAction((Stage) scene.getWindow());
            }
        });
    }

    private boolean isShortcutPressed(KeyEvent event, String shortcut) {
        // Remplacer "Maj" par "Shift" pour correspondre au format attendu par JavaFX
        String formattedShortcut = shortcut.replace("Maj", "Shift");
        
        // Créer une combinaison de touches à partir du raccourci formatté
        KeyCombination keyCombination = KeyCombination.keyCombination(formattedShortcut);
        return keyCombination.match(event);
    }

    private void performSaveAction() {
        System.out.println("Save action triggered!");
    }

    private void performOpenAction() {
        System.out.println("Open action triggered!");
    }

    private void performOpenConfigAction(Stage primaryStage) {
        new ShortcutConfigController(shortcutManager, this).showConfigWindow(primaryStage);
    }

    public void refreshShortcuts() {
        saveButton.setText("Save (Shortcut: " + shortcutManager.getShortcut("save") + ")");
        openButton.setText("Open (Shortcut: " + shortcutManager.getShortcut("open") + ")");
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}