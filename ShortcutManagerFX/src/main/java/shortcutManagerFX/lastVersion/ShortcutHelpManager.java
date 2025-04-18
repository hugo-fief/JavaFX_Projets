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

    /*public static void install() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            private boolean ctrlPressed = false;

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                        ctrlPressed = true;
                    } else if (ctrlPressed && e.getKeyCode() == KeyEvent.VK_H) {
                        Platform.runLater(() -> ShortcutHelpWindow.show());
                        return true;
                    }
                } else if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrlPressed = false;
                }
                return false;
            }
        });
    }*/

    private ShortcutHelpManager() {
        // static only
    }
}
