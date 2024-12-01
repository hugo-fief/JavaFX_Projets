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
import shortcutManagerFX.ShortcutManagerService;
import shortcutManagerFX.model.ShortcutManager;

import java.io.IOException;

/**
 * Contrôleur pour le tableau de bord principal.
 * Gère l'affichage de la liste des raccourcis et l'ouverture de la fenêtre de configuration.
 */
public class DashBoardController {

    @FXML
    private TableView<ShortcutEntry> shortcutTable; // TableView pour afficher les raccourcis

    @FXML
    private TableColumn<ShortcutEntry, String> actionColumn; // Colonne pour les noms des actions

    @FXML
    private TableColumn<ShortcutEntry, String> shortcutColumn; // Colonne pour les raccourcis associés

    private ShortcutManager shortcutManager; // Gestionnaire des raccourcis
    private ShortcutManagerService shortcutManagerService; // Service de gestion des raccourcis globaux

    /**
     * Initialise le contrôleur avec les dépendances nécessaires.
     * Configure la table des raccourcis et enregistre les raccourcis globaux.
     *
     * @param shortcutManager         Le gestionnaire des raccourcis.
     * @param shortcutManagerService  Le service pour enregistrer les raccourcis globaux.
     * @param scene                   La scène associée à ce tableau de bord.
     */
    public void initialize(ShortcutManager shortcutManager, ShortcutManagerService shortcutManagerService, Scene scene) {
        this.shortcutManager = shortcutManager;
        this.shortcutManagerService = shortcutManagerService;

        // Enregistrer les raccourcis globaux pour la scène actuelle
        shortcutManagerService.registerGlobalShortcuts(scene);

        // Configurer les colonnes de la TableView
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        shortcutColumn.setCellValueFactory(new PropertyValueFactory<>("shortcut"));

        // Charger les raccourcis dans la TableView
        ObservableList<ShortcutEntry> shortcuts = FXCollections.observableArrayList();
        for (String action : shortcutManager.getAllShortcuts().keySet()) {
            shortcuts.add(new ShortcutEntry(action, shortcutManager.getShortcut(action)));
        }
        shortcutTable.setItems(shortcuts); // Attacher la liste des raccourcis à la table
    }

    /**
     * Ouvre la fenêtre de configuration des raccourcis.
     * Cette fenêtre permet à l'utilisateur de modifier les raccourcis existants.
     */
    @FXML
    private void openConfigWindow() {
        try {
            // Charger le fichier FXML pour la fenêtre de configuration
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/shortcutManagerFX/ShortcutConfig.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur et l'initialiser avec le ShortcutManager
            ShortcutConfigController configController = loader.getController();
            configController.initialize(shortcutManager);

            // Configurer la fenêtre modale pour la configuration des raccourcis
            Stage stage = new Stage();
            stage.setTitle("Configuration des raccourcis");
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquer l'accès à la fenêtre principale
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Attendre la fermeture de cette fenêtre avant de continuer
        } catch (IOException e) {
            // Gérer les erreurs liées au chargement du fichier FXML
            e.printStackTrace();
        }
    }

    /**
     * Classe interne pour représenter une ligne dans la TableView des raccourcis.
     * Chaque ligne contient une action et le raccourci qui lui est associé.
     */
    public static class ShortcutEntry {
        private final String action; // Nom de l'action
        private final String shortcut; // Raccourci associé

        /**
         * Constructeur pour créer une entrée de raccourci.
         *
         * @param action   Le nom de l'action.
         * @param shortcut Le raccourci associé.
         */
        public ShortcutEntry(String action, String shortcut) {
            this.action = action;
            this.shortcut = shortcut;
        }

        /**
         * Récupère le nom de l'action.
         *
         * @return Le nom de l'action.
         */
        public String getAction() {
            return action;
        }

        /**
         * Récupère le raccourci associé.
         *
         * @return Le raccourci associé.
         */
        public String getShortcut() {
            return shortcut;
        }
    }
}
