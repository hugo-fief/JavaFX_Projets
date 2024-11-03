package employeeManagementSystem.github.techcompany.model;


public class Employee {
    private Integer employeeID;
    private String name;
    private String gender;
    private Integer departmentID;
    private Integer designationID;
    private double salary;
    private String phoneNumber;
    private String epfNumber;
    
    
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
    
    public Integer getEmployeeID(){
        return employeeID;
    }
    public String getName(){
        return name;
    }
    public String getGender(){
        return gender;
    }
    public Integer getDepartmentID(){
        return departmentID;
    }
    public Integer getDesignationID(){
        return designationID;
    }
    public Double getSalary(){
        return salary;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getEpfNumber(){
        return epfNumber;
    }
   
    
}
