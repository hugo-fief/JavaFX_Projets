package techcompany.controller;

import techcompany.model.Admin;
import techcompany.service.AdminService;

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

public class AdminController implements Initializable {

    @FXML
    private PasswordField password;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField username;

    private double x = 0;
    private double y = 0;

    private AdminService adminService = new AdminService();

    public void loginAdmin() {
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill all the blank fields!");
            return;
        }

        Admin admin = adminService.login(username.getText(), password.getText());

        if (admin != null) {
            username = admin.getUsername();
            userRole = admin.getRole();

            showAlert(Alert.AlertType.INFORMATION, "Information Message", "Successfully logged in");

            loadDashBoard();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid Username or Password");
        }
    }

    private void loadDashBoard() {
        try {
            loginBtn.getScene().getWindow().hide();

            Parent root = FXMLLoader.load(getClass().getResource("DashBoardView.fxml"));
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialisation si nécessaire
    }
}
