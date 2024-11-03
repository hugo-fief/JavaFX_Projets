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

    @Override
    public void start(Stage primaryStage) {
        shortcutManager = new ShortcutManager();

        saveButton = new Button("Save (Shortcut: " + shortcutManager.getShortcut("save") + ")");
        saveButton.setOnAction(e -> performSaveAction());

        openButton = new Button("Open (Shortcut: " + shortcutManager.getShortcut("open") + ")");
        openButton.setOnAction(e -> performOpenAction());

        Button configButton = new Button("Configure Shortcuts");
        configButton.setOnAction(e -> new ShortcutConfigController(shortcutManager, this).showConfigWindow());

        VBox layout = new VBox(10, saveButton, openButton, configButton);
        layout.setSpacing(15);
        Scene scene = new Scene(layout, 300, 200);

        // Appliquer le fichier CSS
        File cssFile = new File("src/main/resources/shortcutManagerFX/styles.css");
        scene.getStylesheets().add(cssFile.toURI().toString());

        // Configurer l'écoute des raccourcis clavier
        configureKeyboardShortcuts(scene);

        primaryStage.setScene(scene);
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
            	performOpenConfigAction();
            }
        });
    }

    private boolean isShortcutPressed(KeyEvent event, String shortcut) {
        // Créer une combinaison de touches à partir du raccourci défini
        KeyCombination keyCombination = KeyCombination.keyCombination(shortcut);
        return keyCombination.match(event);
    }

    private void performSaveAction() {
        System.out.println("Save action triggered!");
        // Ajoutez ici le code spécifique pour l'action de sauvegarde
    }

    private void performOpenAction() {
        System.out.println("Open action triggered!");
        // Ajoutez ici le code spécifique pour l'action d'ouverture
    }
    
    private void performOpenConfigAction() {
        // Ouvrir la fenêtre de configuration
        new ShortcutConfigController(shortcutManager, this).showConfigWindow();
    }

    public void refreshShortcuts() {
        saveButton.setText("Save (Shortcut: " + shortcutManager.getShortcut("save") + ")");
        openButton.setText("Open (Shortcut: " + shortcutManager.getShortcut("open") + ")");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
