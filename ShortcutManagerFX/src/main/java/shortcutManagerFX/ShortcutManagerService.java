package shortcutManagerFX;

import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import shortcutManagerFX.model.ShortcutManager;

/**
 * Service pour la gestion des raccourcis globaux dans l'application.
 * Ce service détecte et gère les raccourcis clavier définis dans toute la scène JavaFX.
 */
public class ShortcutManagerService {

    private final ShortcutManager shortcutManager; // Gestionnaire des raccourcis, contenant les définitions actuelles

    /**
     * Constructeur par défaut.
     * Initialise le service avec une instance de ShortcutManager.
     */
    public ShortcutManagerService() {
        this.shortcutManager = new ShortcutManager();
    }

    /**
     * Enregistre les raccourcis globaux sur une scène donnée.
     * Tous les raccourcis définis dans le ShortcutManager seront actifs sur cette scène.
     *
     * @param scene La scène JavaFX où les raccourcis doivent être enregistrés.
     */
    public void registerGlobalShortcuts(Scene scene) {
        // Ajouter un filtre d'événements pour intercepter les pressions de touches
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            // Parcourir toutes les actions définies dans ShortcutManager
            for (String action : shortcutManager.getAllShortcuts().keySet()) {
                // Récupérer le raccourci pour l'action
                String shortcut = shortcutManager.getShortcut(action);

                // Créer une combinaison de touches à partir du raccourci
                KeyCombination keyCombination = KeyCombination.keyCombination(shortcut);

                // Vérifier si le raccourci saisi correspond à la combinaison
                if (keyCombination.match(event)) {
                    // Déclencher l'action associée
                    handleShortcutAction(action);
                    event.consume(); // Empêche la propagation de l'événement dans la scène
                    break; // Arrêter la boucle après avoir trouvé une correspondance
                }
            }
        });
    }

    /**
     * Gère les actions associées aux raccourcis lorsqu'elles sont déclenchées.
     *
     * @param action Le nom de l'action associée au raccourci.
     */
    private void handleShortcutAction(String action) {
        switch (action) {
            case "action1":
                // Action 1 : Exemple d'opération
                System.out.println("Action 1 déclenchée !");
                break;

            case "action2":
                // Action 2 : Exemple d'opération
                System.out.println("Action 2 déclenchée !");
                break;

            case "config":
                // Action pour ouvrir la configuration des raccourcis
                System.out.println("Ouvrir la configuration des raccourcis.");
                break;

            default:
                // Gestion des actions non reconnues
                System.out.println("Action non reconnue : " + action);
                break;
        }
    }
}
