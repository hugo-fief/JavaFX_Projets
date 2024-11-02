package employee.management.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "designation")
public class Designation {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public void setDesignationID(int designationID) {
		this.designationID = designationID;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
}
