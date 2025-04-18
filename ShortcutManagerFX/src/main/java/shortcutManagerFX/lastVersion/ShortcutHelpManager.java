package shortcutManagerFX.lastVersion;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Gère les raccourcis clavier globaux de l'application.
 * Ajoute un raccourci Ctrl + H pour ouvrir la fenêtre d'aide.
 */
public final class ShortcutHelpManager {

    public static void install(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.H) {
                ShortcutHelpWindow.show();
            }
        });
    }

    private ShortcutHelpManager() {
        // static only
    }
}
