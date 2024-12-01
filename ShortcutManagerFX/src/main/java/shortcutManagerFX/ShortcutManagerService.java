package shortcutManagerFX;

import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import shortcutManagerFX.model.ShortcutManager;

public class ShortcutManagerService {

    private final ShortcutManager shortcutManager;

    public ShortcutManagerService() {
        this.shortcutManager = new ShortcutManager();
    }

    public void registerGlobalShortcuts(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            for (String action : shortcutManager.getAllShortcuts().keySet()) {
                String shortcut = shortcutManager.getShortcut(action);
                KeyCombination keyCombination = KeyCombination.keyCombination(shortcut);

                if (keyCombination.match(event)) {
                    handleShortcutAction(action);
                    event.consume(); // Empêche la propagation de l'événement
                    break;
                }
            }
        });
    }

    private void handleShortcutAction(String action) {
        switch (action) {
            case "action1":
                System.out.println("Action 1 déclenchée !");
                break;
            case "action2":
                System.out.println("Action 2 déclenchée !");
                break;
            case "config":
                System.out.println("Ouvrir la configuration des raccourcis.");
                break;
            default:
                System.out.println("Action non reconnue : " + action);
                break;
        }
    }
}
