package techcompany.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeID;
    private String name;
    private String gender;
    private Integer departmentID;
    private Integer designationID;
    private double salary;
    private String phoneNumber;
    private String epfNumber;
    
	public Employee() {

	}
    
    public Employee(Integer employeeID, String name, String gender, Integer departmentID, Integer designationID, double salary, String phoneNumber, String epfNumber){
        this.employeeID = employeeID;
        this.name = name;
        this.gender = gender;
        this.departmentID = departmentID;
        this.designationID = designationID;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.epfNumber = epfNumber;
    }


	public Integer getEmployeeID() {
		return employeeID;
	}


	public void setEmployeeID(Integer employeeID) {
		this.employeeID = employeeID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public Integer getDepartmentID() {
		return departmentID;
	}


	public void setDepartmentID(Integer departmentID) {
		this.departmentID = departmentID;
	}


	public Integer getDesignationID() {
		return designationID;
	}


	public void setDesignationID(Integer designationID) {
		this.designationID = designationID;
	}


	public double getSalary() {
		return salary;
	}


	public void setSalary(double salary) {
		this.salary = salary;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getEpfNumber() {
		return epfNumber;
	}


	public void setEpfNumber(String epfNumber) {
		this.epfNumber = epfNumber;
	}
}
