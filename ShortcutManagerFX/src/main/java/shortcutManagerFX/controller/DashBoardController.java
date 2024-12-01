package shortcutManagerFX.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shortcutManagerFX.model.ShortcutManager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Contrôleur principal pour l'interface des raccourcis.
 * Gère l'affichage et les interactions avec la liste des raccourcis.
 */
public class DashBoardController {

    @FXML
    private TableView<ShortcutEntry> shortcutTable; // Tableau affichant les raccourcis

    @FXML
    private TableColumn<ShortcutEntry, String> actionColumn; // Colonne pour les actions

    @FXML
    private TableColumn<ShortcutEntry, String> shortcutColumn; // Colonne pour les raccourcis

    private ShortcutManager shortcutManager; // Gestionnaire des raccourcis
    private final Set<String> pressedKeys = new HashSet<>();

    /**
     * Initialise le contrôleur avec le gestionnaire des raccourcis.
     * Charge les raccourcis dans le tableau.
     *
     * @param shortcutManager Gestionnaire des raccourcis.
     */
    public void initialize(ShortcutManager shortcutManager) {
        this.shortcutManager = shortcutManager;

        // Configuration des colonnes du tableau
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        shortcutColumn.setCellValueFactory(new PropertyValueFactory<>("shortcut"));

        // Charger les raccourcis dans le tableau
        refreshTable();
        
        // Ajouter un écouteur global pour détecter les raccourcis
        shortcutTable.setOnKeyPressed(event -> {
            // Ajouter la touche actuelle au set
            pressedKeys.add(event.getCode().getName());

            // Construire la combinaison actuelle
            String currentCombination = String.join("+", pressedKeys);

            // Vérifier si cette combinaison correspond à un raccourci
            shortcutManager.getAllShortcuts().forEach((action, shortcut) -> {
                if (shortcut.equalsIgnoreCase(currentCombination)) {
                    handleShortcutAction(action); // Appeler l'action associée
                }
            });
        });

        shortcutTable.setOnKeyReleased(event -> {
            // Retirer la touche relâchée du set
            pressedKeys.remove(event.getCode().getName());
        });
    }
    
    /**
     * Exécute une action en fonction de son nom.
     *
     * @param action Le nom de l'action à exécuter (ex : "save", "open").
     */
    private void handleShortcutAction(String action) {
        switch (action) {
            case "save":
                System.out.println("Action 'Save' exécutée !");
                // Ajouter ici le code pour sauvegarder les données
                break;

            case "open":
                System.out.println("Action 'Open' exécutée !");
                // Ajouter ici le code pour ouvrir un fichier
                break;

            default:
                System.out.println("Action inconnue : " + action);
                break;
        }
    }

    /**
     * Ouvre la fenêtre de configuration des raccourcis.
     * Notifie le tableau principal pour actualiser les données après une modification.
     */
    @FXML
    private void openConfigWindow() {
        try {
            // Charger l'interface de configuration
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/shortcutManagerFX/ShortcutConfig.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur associé
            ShortcutConfigController configController = loader.getController();
            configController.initialize(shortcutManager);

            // Définir une action pour rafraîchir le tableau après modification
            configController.setOnShortcutChangeListener(this::refreshTable);

            // Configurer et afficher la fenêtre
            Stage stage = new Stage();
            stage.setTitle("Configure Shortcuts");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Attendre la fermeture de la fenêtre

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rafraîchit les données du tableau en rechargeant les raccourcis depuis le gestionnaire.
     */
    private void refreshTable() {
        ObservableList<ShortcutEntry> shortcuts = FXCollections.observableArrayList();
        for (String action : shortcutManager.getAllShortcuts().keySet()) {
            shortcuts.add(new ShortcutEntry(action, shortcutManager.getShortcut(action)));
        }
        shortcutTable.setItems(shortcuts); // Mettre à jour les données affichées
    }

    /**
     * Classe interne représentant une entrée dans le tableau des raccourcis.
     */
    public static class ShortcutEntry {
        private final String action; // Nom de l'action
        private final String shortcut; // Raccourci associé

        /**
         * Constructeur pour une entrée de raccourci.
         *
         * @param action   Nom de l'action.
         * @param shortcut Raccourci associé.
         */
        public ShortcutEntry(String action, String shortcut) {
            this.action = action;
            this.shortcut = shortcut;
        }

        public String getAction() {
            return action;
        }

        public String getShortcut() {
            return shortcut;
        }
    }
}
