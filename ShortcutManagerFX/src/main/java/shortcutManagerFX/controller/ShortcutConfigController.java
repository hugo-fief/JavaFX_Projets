package shortcutManagerFX.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import shortcutManagerFX.model.ShortcutManager;
import shortcutManagerFX.util.ShortcutValidator;

/**
 * Contrôleur pour la fenêtre de configuration des raccourcis.
 * Permet à l'utilisateur de visualiser, modifier et valider les raccourcis clavier.
 */
public class ShortcutConfigController {

    @FXML
    private TextField saveField; // Champ pour configurer le raccourci "Save"

    @FXML
    private TextField openField; // Champ pour configurer le raccourci "Open"

    private ShortcutManager shortcutManager; // Gestionnaire des raccourcis

    /**
     * Initialise le contrôleur avec le gestionnaire de raccourcis.
     * Cette méthode configure les champs de raccourcis avec les valeurs existantes et
     * applique des gestionnaires spécifiques pour gérer la saisie utilisateur.
     *
     * @param shortcutManager Le gestionnaire des raccourcis, contenant les données actuelles.
     */
    public void initialize(ShortcutManager shortcutManager) {
        this.shortcutManager = shortcutManager;

        // Remplir les champs avec les raccourcis actuels
        saveField.setText(shortcutManager.getShortcut("save"));
        openField.setText(shortcutManager.getShortcut("open"));

        // Appliquer un gestionnaire de saisie pour chaque champ
        initializeShortcutField(saveField);
        initializeShortcutField(openField);
    }

    /**
     * Configure un champ de texte pour gérer les raccourcis avec les règles suivantes :
     * - La suppression du dernier segment est déclenchée par "Backspace" ou "Delete".
     * - Les touches saisies sont automatiquement converties en majuscules et préfixées par "+".
     * - Le raccourci doit commencer par "Ctrl".
     *
     * @param shortcutField Le champ de texte à configurer.
     */
    private void initializeShortcutField(TextField shortcutField) {
        shortcutField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            String currentText = shortcutField.getText(); // Texte actuel dans le champ
            KeyCode keyCode = event.getCode(); // Code de la touche pressée

            // Gestion des touches spéciales
            if (keyCode == KeyCode.BACK_SPACE || keyCode == KeyCode.DELETE) {
                // Supprime le dernier segment du raccourci (jusqu'au dernier "+")
                int lastPlusIndex = currentText.lastIndexOf("+");
                if (lastPlusIndex != -1) {
                    shortcutField.setText(currentText.substring(0, lastPlusIndex));
                } else {
                    shortcutField.clear(); // Efface tout si aucun "+" n'est trouvé
                }
                event.consume();
            } else if (keyCode == KeyCode.CONTROL) {
                // Vérifie si "Ctrl" est présent et l'ajoute au début si le champ est vide
                if (currentText.isEmpty()) {
                    shortcutField.setText("Ctrl");
                }
                event.consume();
            } else if (keyCode == KeyCode.ALT) {
        		// Ajouter "Alt" si "Ctrl" est déjà présent
                if (currentText.startsWith("Ctrl") && !currentText.contains("Alt")) {
                    shortcutField.setText(currentText + "+Alt");
                }
                event.consume();
            } else if (keyCode == KeyCode.SHIFT) {
            	// Ajouter "Shift" si "Ctrl" est déjà présent
                if (currentText.startsWith("Ctrl") && !currentText.contains("Shift")) {
                    shortcutField.setText(currentText + "+Maj");
                }
                event.consume();
            } else if (!currentText.isEmpty() && currentText.startsWith("Ctrl")) {
            	// Ajouter une touche après "Ctrl" avec un "+"
                String keyText = keyCode.getName().toUpperCase(); // Nom de la touche (ex. : "A", "SHIFT")
            	
                // Ajouter la touche saisie avec un "+" (si Ctrl est présent)
                if (!keyText.equals("CTRL") && !keyText.equals("ALT") && !keyText.equals("SHIFT")) {
                    shortcutField.setText(currentText + "+" + keyText);
                }
            } else {
                // Si "Ctrl" n'est pas présent, la saisie est refusée
                event.consume();
            }
        });
    }

    /**
     * Vide le champ "Save Shortcut".
     * Cette méthode est appelée par le bouton "X" associé à ce champ.
     */
    @FXML
    private void clearSaveField() {
        saveField.clear();
    }

    /**
     * Vide le champ "Open Shortcut".
     * Cette méthode est appelée par le bouton "X" associé à ce champ.
     */
    @FXML
    private void clearOpenField() {
        openField.clear();
    }

    /**
     * Valide et sauvegarde les raccourcis saisis par l'utilisateur.
     * Si un raccourci est invalide, une boîte de dialogue d'erreur est affichée.
     */
    @FXML
    private void saveChanges() {
        String saveShortcut = saveField.getText(); // Récupérer le raccourci "Save"
        String openShortcut = openField.getText(); // Récupérer le raccourci "Open"

        // Validation des raccourcis
        if (!ShortcutValidator.isValidShortcut(saveShortcut)) {
            showErrorAlert("Invalid Save Shortcut", ShortcutValidator.getInvalidShortcutMessage());
            return;
        }
        if (!ShortcutValidator.isValidShortcut(openShortcut)) {
            showErrorAlert("Invalid Open Shortcut", ShortcutValidator.getInvalidShortcutMessage());
            return;
        }
        
        // Vérification des doublons avec la liste complète des raccourcis
        if (shortcutManager.isShortcutDuplicate(saveShortcut, "save")) {
            showErrorAlert("Duplicate Shortcut", "The shortcut '" + saveShortcut + "' is already assigned to another action.");
            saveField.requestFocus();
            return;
        }
        if (shortcutManager.isShortcutDuplicate(openShortcut, "open")) {
            showErrorAlert("Duplicate Shortcut", "The shortcut '" + openShortcut + "' is already assigned to another action.");
            openField.requestFocus();
            return;
        }

        // Mise à jour des raccourcis dans le gestionnaire
        shortcutManager.setShortcut("save", saveShortcut);
        shortcutManager.setShortcut("open", openShortcut);

        // Sauvegarde des raccourcis dans le fichier de configuration
        shortcutManager.saveShortcuts();

        System.out.println("Raccourcis mis à jour avec succès !");
    }

    /**
     * Affiche une boîte de dialogue d'erreur avec un message spécifique.
     *
     * @param title   Le titre de l'erreur affichée.
     * @param message Le message détaillé de l'erreur.
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null); // Pas de sous-titre
        alert.setContentText(message); // Message de l'erreur
        alert.showAndWait();
    }
}
