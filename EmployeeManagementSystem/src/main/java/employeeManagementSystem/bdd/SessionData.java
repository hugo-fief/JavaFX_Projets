package employeeManagementSystem.bdd;

/**
 * Singleton class for managing user session data (e.g., username and role).
 */
public class SessionData {

    private static SessionData instance;
    private String username;
    private String userRole;

    // Constructeur privé pour le Singleton
    private SessionData() {}

    /**
     * Get the singleton instance of SessionData.
     * 
     * @return the singleton instance
     */
    public static synchronized SessionData getInstance() {
        if (instance == null) {
            instance = new SessionData();
        }
        return instance;
    }

    // Getter et Setter pour le nom d'utilisateur
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter et Setter pour le rôle de l'utilisateur
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}

