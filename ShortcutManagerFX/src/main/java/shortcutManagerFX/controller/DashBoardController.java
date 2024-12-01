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

public class DashBoardController {

    @FXML
    private TableView<ShortcutEntry> shortcutTable;

    @FXML
    private TableColumn<ShortcutEntry, String> actionColumn;

    @FXML
    private TableColumn<ShortcutEntry, String> shortcutColumn;

    private ShortcutManager shortcutManager;
    private ShortcutManagerService shortcutManagerService;

    public void initialize(ShortcutManager shortcutManager, ShortcutManagerService shortcutManagerService, Scene scene) {
        this.shortcutManager = shortcutManager;
        this.shortcutManagerService = shortcutManagerService;
        shortcutManagerService.registerGlobalShortcuts(scene);

        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        shortcutColumn.setCellValueFactory(new PropertyValueFactory<>("shortcut"));

        ObservableList<ShortcutEntry> shortcuts = FXCollections.observableArrayList();
        for (String action : shortcutManager.getAllShortcuts().keySet()) {
            shortcuts.add(new ShortcutEntry(action, shortcutManager.getShortcut(action)));
        }
        shortcutTable.setItems(shortcuts);
    }

    @FXML
    private void openConfigWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/shortcutManagerFX/ShortcutConfig.fxml"));
            Parent root = loader.load();

            ShortcutConfigController configController = loader.getController();
            configController.initialize(shortcutManager);

            Stage stage = new Stage();
            stage.setTitle("Configuration des raccourcis");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ShortcutEntry {
        private final String action;
        private final String shortcut;

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
