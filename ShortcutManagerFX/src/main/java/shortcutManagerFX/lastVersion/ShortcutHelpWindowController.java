package shortcutManagerFX.lastVersion;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ShortcutHelpWindowController {

    @FXML
    private TableView<ShortcutEntry> shortcutTable;

    @FXML
    private TableColumn<ShortcutEntry, String> columnKey;

    @FXML
    private TableColumn<ShortcutEntry, String> columnAction;

    @FXML
    public void initialize() {
        setupTableColumns();         // Configure les colonnes
        fillShortcuts();            // Remplit les raccourcis
        applyRowStyling();          // Gère l'alternance des lignes + hover
        applyFocusAndAnimations();  // Applique les animations d'affichage
    }

    // Association des champs du modèle à chaque colonne
    private void setupTableColumns() {
        columnKey.setCellValueFactory(cell -> cell.getValue().keyProperty());
        columnAction.setCellValueFactory(cell -> cell.getValue().actionProperty());
    }

    // Liste des raccourcis clavier à afficher dans le tableau
    private void fillShortcuts() {
        ObservableList<ShortcutEntry> shortcuts = FXCollections.observableArrayList(
          new ShortcutEntry("Ctrl + H", "Afficher cette fenêtre"),
          new ShortcutEntry("Echap", "Fermer cette fenêtre"),
          new ShortcutEntry("Ctrl + S", "Sauvegarder le projet"),
          new ShortcutEntry("Ctrl + O", "Ouvrir le projet"),
          new ShortcutEntry("Ctrl + D", "Supprimer une indication"),
          new ShortcutEntry("Ctrl + L", "Verrouiller une indication"),
          new ShortcutEntry("Ctrl + G", "Recalage PA")
        );
        
        shortcutTable.setItems(shortcuts);
    }

    // Applique :
    //    - une alternance blanc / gris clair (pair/impair)
    //    - un effet hover (ligne survolée)
    private void applyRowStyling() {
        shortcutTable.setRowFactory(tv -> {
            TableRow<ShortcutEntry> row = new TableRow<ShortcutEntry>() {
                @Override
                protected void updateItem(ShortcutEntry item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setStyle("");
                    } else {
                        int index = getIndex();
                        String baseColor = (index % 2 == 0) ? "#ffffff" : "#f9f9f9";
                        setStyle("-fx-background-color: " + baseColor + ";");
                    }
                }
            };

            // Effet de survol (hover)
            row.hoverProperty().addListener((obs, oldVal, isHovered) -> {
                if (isHovered && !row.isEmpty()) {
                    row.setStyle("-fx-background-color: #f0f0f0;");
                } else {
                    int index = row.getIndex();
                    String baseColor = (index % 2 == 0) ? "#ffffff" : "#f9f9f9";
                    row.setStyle("-fx-background-color: " + baseColor + ";");
                }
            });

            return row;
        });
    }

    // Applique :
    //    - le focus automatique sur la 1ère ligne
    //    - le slide-in vertical progressif
    //    - la pulsation douce sur la 1ère ligne
    private void applyFocusAndAnimations() {
        Platform.runLater(() -> {
            shortcutTable.scrollTo(0);
            shortcutTable.getSelectionModel().selectFirst();

            // Animation d'apparition en slide-in
            for (int i = 0; i < shortcutTable.getItems().size(); i++) {
                Node row = shortcutTable.lookup(".table-row-cell:nth-child(" + (i + 1) + ")");
                if (row != null) {
                    TranslateTransition tt = new TranslateTransition(Duration.millis(300), row);
                    tt.setFromY(20);
                    tt.setToY(0);
                    tt.setDelay(Duration.millis(i * 50)); // décalage progressif
                    tt.play();
                }
            }

            // Pulsation légère de la 1ère ligne
            Node firstRow = shortcutTable.lookup(".table-row-cell");
            if (firstRow != null) {
                ScaleTransition pulse = new ScaleTransition(Duration.millis(400), firstRow);
                pulse.setFromX(1);
                pulse.setToX(1.02);
                pulse.setFromY(1);
                pulse.setToY(1.02);
                pulse.setCycleCount(2);
                pulse.setAutoReverse(true);
                pulse.play();
            }
        });
    }

    // Fermer la fenêtre avec un effet "scale" doux
    @FXML
    public void closeWindow(ActionEvent event) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(150), ((Node) event.getSource()));
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(0.9);
        scale.setToY(0.9);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.setOnFinished(e -> ((Stage)((Node) event.getSource()).getScene().getWindow()).close());
        scale.play();
    }

    // Modèle de donnée pour chaque ligne
    public static class ShortcutEntry {
        private final SimpleStringProperty key;
        private final SimpleStringProperty action;

        public ShortcutEntry(String key, String action) {
            this.key = new SimpleStringProperty(key);
            this.action = new SimpleStringProperty(action);
        }

        public String getKey() { return key.get(); }
        public String getAction() { return action.get(); }

        public SimpleStringProperty keyProperty() { return key; }
        public SimpleStringProperty actionProperty() { return action; }
    }
}
