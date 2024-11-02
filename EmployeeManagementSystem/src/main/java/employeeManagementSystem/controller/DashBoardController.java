package employeeManagementSystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import employeeManagementSystem.model.Employee;
import employeeManagementSystem.service.EmployeeService;

public class DashBoardController {

    private EmployeeService employeeService = new EmployeeService();
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
    private TextField addEmployee_employeeID;
    @FXML
    private TextField addEmployee_name;
    @FXML
    private ComboBox<String> addEmployee_gender;
    @FXML
    private ComboBox<String> addEmployee_depart;
    @FXML
    private ComboBox<String> addEmployee_desig;
    @FXML
    private TextField addEmployee_salary;
    @FXML
    private TextField addEmployee_phoneNum;
    @FXML
    private TextField addEmployee_epfNo;
    @FXML
    private Button addEmployee_btn;

    public void initialize() {
        setupTableColumns();
        loadEmployeeData();
    }

    private void setupTableColumns() {
        addEmployee_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        addEmployee_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        addEmployee_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addEmployee_col_depart.setCellValueFactory(new PropertyValueFactory<>("departmentID"));
        addEmployee_col_desig.setCellValueFactory(new PropertyValueFactory<>("designationID"));
        addEmployee_col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        addEmployee_col_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addEmployee_col_epfNo.setCellValueFactory(new PropertyValueFactory<>("epfNumber"));
    }

    private void loadEmployeeData() {
        employeeList = FXCollections.observableArrayList(employeeService.getAllEmployees());
        addEmployee_tableView.setItems(employeeList);
    }

    public void addOrUpdateEmployee() {
        if (addEmployee_employeeID.getText().isEmpty() || addEmployee_name.getText().isEmpty() || addEmployee_gender.getSelectionModel().isEmpty() ||
            addEmployee_depart.getSelectionModel().isEmpty() || addEmployee_desig.getSelectionModel().isEmpty() || addEmployee_salary.getText().isEmpty() ||
            addEmployee_phoneNum.getText().isEmpty() || addEmployee_epfNo.getText().isEmpty()) {
            showAlert("Error", "Please fill all fields");
            return;
        }

        Employee employee = new Employee();
        employee.setEmployeeID(Integer.parseInt(addEmployee_employeeID.getText()));
        employee.setName(addEmployee_name.getText());
        employee.setGender(addEmployee_gender.getSelectionModel().getSelectedItem());
        // Assuming you have methods to convert department and designation names to IDs
        employee.setDepartmentID(getDepartmentID(addEmployee_depart.getSelectionModel().getSelectedItem()));
        employee.setDesignationID(getDesignationID(addEmployee_desig.getSelectionModel().getSelectedItem()));
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
            employeeService.deleteEmployee(selectedEmployee.getEmployeeID());
            loadEmployeeData();
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private int getDepartmentID(String departmentName) {
        // Implement a lookup method to get the department ID based on the name
        // This can use another service or a cache
        return 0;
    }

    private int getDesignationID(String designationName) {
        // Implement a lookup method to get the designation ID based on the name
        // This can use another service or a cache
        return 0;
    }
}
