package employeeManagementSystem.controller;

import employeeManagementSystem.model.Employee;
import employeeManagementSystem.model.Department;
import employeeManagementSystem.model.Designation;
import employeeManagementSystem.service.EmployeeService;
import employeeManagementSystem.service.DepartmentService;
import employeeManagementSystem.service.DesignationService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class DashBoardController {

    private EmployeeService employeeService = new EmployeeService();
    private DepartmentService departmentService = new DepartmentService();
    private DesignationService designationService = new DesignationService();

    @FXML private TableView<Employee> addEmployee_tableView;
    @FXML private TableColumn<Employee, Integer> addEmployee_col_employeeID;
    @FXML private TableColumn<Employee, String> addEmployee_col_name;
    @FXML private TableColumn<Employee, String> addEmployee_col_gender;
    @FXML private TableColumn<Employee, String> addEmployee_col_depart;
    @FXML private TableColumn<Employee, String> addEmployee_col_desig;
    @FXML private TableColumn<Employee, Double> addEmployee_col_salary;
    @FXML private TextField addEmployee_employeeID;
    @FXML private TextField addEmployee_name;

    // Other FXML elements...

    public void initialize() {
        setupTableColumns();
        loadEmployeeData();
    }

    private void setupTableColumns() {
        /*
    	addEmployee_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        addEmployee_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        addEmployee_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addEmployee_col_depart.setCellValueFactory(cellData -> new SimpleStringProperty(
        departmentService.getDepartmentID(cellData.getValue().getDepartmentID()).getDepartmentName()));
        addEmployee_col_desig.setCellValueFactory(cellData -> new SimpleStringProperty(
        designationService.getDepartmentID(cellData.getValue().getDesignationID()).getDesignationName()));
        addEmployee_col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    	*/
    }

    private void loadEmployeeData() {
        List<Employee> employees = employeeService.getAllEmployees();
        ObservableList<Employee> employeeList = FXCollections.observableArrayList(employees);
        addEmployee_tableView.setItems(employeeList);
    }

    public void addOrUpdateEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeID(Integer.parseInt(addEmployee_employeeID.getText()));
        employee.setName(addEmployee_name.getText());
        // Set other properties...

        employeeService.saveOrUpdateEmployee(employee);
        loadEmployeeData();
        clearFields();
    }

    private void clearFields() {
        addEmployee_employeeID.clear();
        addEmployee_name.clear();
        // Clear other fields...
    }
}
