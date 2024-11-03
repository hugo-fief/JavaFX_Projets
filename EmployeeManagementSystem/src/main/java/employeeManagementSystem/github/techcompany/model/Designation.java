package employeeManagementSystem.github.techcompany.model;


public class Designation {
    
    private int designationID;
    private String designationName;
    
    public Designation(int designationID, String designationName) {
        this.designationID = designationID;
        this.designationName = designationName;
    }
    
     public int getDesignationID() {
        return designationID;
    }

    public String getDesignationName() {
        return designationName;
    }
    
}
