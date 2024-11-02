package techcompany.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class for establishing a single database connection instance.
*/
public class DataBase {

    private static final String URL = "jdbc:mysql://localhost/employee";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static DataBase instance; // Singleton instance
    private Connection connection;

    /**
     * Private constructor to prevent external instantiation.
    */
    private DataBase() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    /**
     * Returns the singleton instance of the DataBase class.
     * 
     * @return DataBase instance
    */
    public static synchronized DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    /**
     * Returns the database connection.
     * 
     * @return Connection object if connected, otherwise null.
    */
    public Connection getConnection() {
        return connection;
    }
}
