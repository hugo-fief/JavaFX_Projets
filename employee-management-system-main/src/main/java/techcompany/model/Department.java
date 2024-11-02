package techcompany.model;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "department")
public class Department {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentID;
    private String departmentName;
    
    public Department(int departmentID, String departmentName) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
    }
    
     public int getDepartmentID() {
        return departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }
    
    public void setDepartmentID(int departmentID) {
    	this.departmentID = departmentID;
    }
    
    public void setDepartmentName(String departmentName) {
    	this.departmentName = departmentName;
    }
}
