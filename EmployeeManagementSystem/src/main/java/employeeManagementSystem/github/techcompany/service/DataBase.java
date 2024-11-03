package employeeManagementSystem.github.techcompany.service;


import java.sql.Connection;
import java.sql.DriverManager;


public class DataBase {
    
    public static Connection connectDb(){
    
        try{
  
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/employee","root","");
            return connect;
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    
    
    }
    
}
