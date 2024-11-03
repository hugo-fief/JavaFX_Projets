package employeeManagementSystem.github.techcompany.model;


public class Admin {
    
    private String username;
    private String role;
    
    public Admin(String username, String role) {
        this.username = username;
        this.role = role;
    }
    
    public String getAdminUsername() {
        return username;
    }

    public String getAdminUserRole() {
        return role;
    }
}

