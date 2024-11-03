package employeeManagementSystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Optional;

import employeeManagementSystem.model.Employee;
import employeeManagementSystem.service.EmployeeService;
import employeeManagementSystem.service.DepartmentService;
import employeeManagementSystem.service.DesignationService;

public class DashBoardController {

    private final EmployeeService employeeService = new EmployeeService();
    private final DepartmentService departmentService = new DepartmentService();
    private final DesignationService designationService = new DesignationService();

    private ObservableList<Employee> employeeList;

    @FXML
    private TableView<Employee> addEmployee_tableView;
    @FXML
    private TableColumn<Employee, Integer> addEmployee_col_employeeID;
    @FXML
    private TableColumn<Employee, String> addEmployee_col_name;
    @FXML
    private TableColumn<Employee, String> addEmployee_col_gender;
    @FXML
    private TableColumn<Employee, String> addEmployee_col_depart;
    @FXML
    private TableColumn<Employee, String> addEmployee_col_desig;
    @FXML
    private TableColumn<Employee, Double> addEmployee_col_salary;
    @FXML
    private TableColumn<Employee, String> addEmployee_col_phoneNum;
    @FXML
    private TableColumn<Employee, String> addEmployee_col_epfNo;
    
    @FXML
    private TextField addEmployee_employeeID, addEmployee_name, addEmployee_salary, addEmployee_phoneNum, addEmployee_epfNo;
    @FXML
    private ComboBox<String> addEmployee_gender, addEmployee_depart, addEmployee_desig;
    @FXML
    private Button addEmployee_btn, close, minimize, logout;

    @FXML
    private AnchorPane home_form, addEmployee_form, addUser_form, depDesig_form;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadEmployeeData();
        populateComboBoxes();
    }

    private void setupTableColumns() {
        addEmployee_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        addEmployee_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        addEmployee_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addEmployee_col_depart.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        addEmployee_col_desig.setCellValueFactory(new PropertyValueFactory<>("designationName"));
        addEmployee_col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        addEmployee_col_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addEmployee_col_epfNo.setCellValueFactory(new PropertyValueFactory<>("epfNumber"));
    }

    private void loadEmployeeData() {
        employeeList = FXCollections.observableArrayList(employeeService.getAllEmployees());
        addEmployee_tableView.setItems(employeeList);
    }

    private void populateComboBoxes() {
        addEmployee_gender.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        addEmployee_depart.setItems(FXCollections.observableArrayList(departmentService.getDepartmentNames()));
        addEmployee_desig.setItems(FXCollections.observableArrayList(designationService.getDesignationNames()));
    }

    public void addOrUpdateEmployee() {
        if (areFieldsEmpty()) {
            showAlert("Error", "Please fill all fields");
            return;
        }

        Employee employee = new Employee();
        employee.setEmployeeID(Integer.parseInt(addEmployee_employeeID.getText()));
        employee.setName(addEmployee_name.getText());
        employee.setGender(addEmployee_gender.getSelectionModel().getSelectedItem());
        employee.setDepartmentID(departmentService.getDepartmentID(addEmployee_depart.getSelectionModel().getSelectedItem()));
        employee.setDesignationID(designationService.getDesignationID(addEmployee_desig.getSelectionModel().getSelectedItem()));
        employee.setSalary(Double.parseDouble(addEmployee_salary.getText()));
        employee.setPhoneNumber(addEmployee_phoneNum.getText());
        employee.setEpfNumber(addEmployee_epfNo.getText());

        employeeService.saveOrUpdateEmployee(employee);
        loadEmployeeData();
        clearFields();
    }

    public void deleteEmployee() {
        Employee selectedEmployee = addEmployee_tableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            Optional<ButtonType> result = showAlert("Confirmation", "Are you sure you want to delete this employee?", Alert.AlertType.CONFIRMATION);
            if (result.isPresent() && result.get() == ButtonType.OK) {
                employeeService.deleteEmployee(selectedEmployee.getEmployeeID());
                loadEmployeeData();
            }
        } else {
            showAlert("Error", "Please select an employee to delete");
        }
    }

    private void clearFields() {
        addEmployee_employeeID.clear();
        addEmployee_name.clear();
        addEmployee_gender.getSelectionModel().clearSelection();
        addEmployee_depart.getSelectionModel().clearSelection();
        addEmployee_desig.getSelectionModel().clearSelection();
        addEmployee_salary.clear();
        addEmployee_phoneNum.clear();
        addEmployee_epfNo.clear();
    }

    private boolean areFieldsEmpty() {
        return addEmployee_employeeID.getText().isEmpty() || addEmployee_name.getText().isEmpty() ||
               addEmployee_gender.getSelectionModel().isEmpty() || addEmployee_depart.getSelectionModel().isEmpty() ||
               addEmployee_desig.getSelectionModel().isEmpty() || addEmployee_salary.getText().isEmpty() ||
               addEmployee_phoneNum.getText().isEmpty() || addEmployee_epfNo.getText().isEmpty();
    }

    private Optional<ButtonType> showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    private void showAlert(String title, String message) {
        showAlert(title, message, Alert.AlertType.INFORMATION);
    }

    @FXML
    private void close() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimize() {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void logout() {
        Optional<ButtonType> result = showAlert("Logout", "Are you sure you want to log out?", Alert.AlertType.CONFIRMATION);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void switchForm() {
        home_form.setVisible(false);
        addEmployee_form.setVisible(true);
        addUser_form.setVisible(false);
        depDesig_form.setVisible(false);
    }
}
