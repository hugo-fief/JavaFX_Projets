package employeeManagementSystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

import employeeManagementSystem.model.Admin;
import employeeManagementSystem.service.AdminService;

public class AdminController implements Initializable {

    @FXML
    private PasswordField password;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField username;

    private double x = 0;
    private double y = 0;

    private final AdminService adminService = new AdminService();

    /**
     * Handles the login action by authenticating the user and loading the dashboard.
     */
    public void loginAdmin() {
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill all the blank fields!");
            return;
        }

        Admin admin = adminService.login(username.getText(), password.getText());

        if (admin != null) {
            showAlert(Alert.AlertType.INFORMATION, "Information Message", "Successfully logged in");
            loadDashboard();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid Username or Password");
        }
    }
    
    /**
     * Closes the current window.
    */
    @FXML
    private void close() {
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Loads the dashboard view after successful login.
     */
    private void loadDashboard() {
        try {
            loginBtn.getScene().getWindow().hide();

            Parent root = FXMLLoader.load(getClass().getResource("/techcompany/view/DashboardView.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays an alert with a given type, title, and message.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Any necessary initialization can be done here
    }
}
