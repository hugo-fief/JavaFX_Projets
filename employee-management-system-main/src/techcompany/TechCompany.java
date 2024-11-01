package com.techcompany;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main application class that starts the JavaFX application.
*/
public class MainApp extends Application {
    private static final double DRAG_OPACITY = 0.8;
    private static final double DEFAULT_OPACITY = 1.0;
    
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));

        Scene scene = new Scene(root);
        setupWindowDragging(stage, root);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes dragging behavior for the stage window.
     * @param stage The primary stage of the application.
     * @param root The root node of the scene graph.
    */
    private void setupWindowDragging(Stage stage, Parent root) {
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
            stage.setOpacity(DRAG_OPACITY);
        });

        root.setOnMouseReleased(event -> stage.setOpacity(DEFAULT_OPACITY));
    }

    /**
     * Main entry point for the application.
     * @param args command line arguments
    */
    public static void main(String[] args) {
        launch(args);
    }
}
