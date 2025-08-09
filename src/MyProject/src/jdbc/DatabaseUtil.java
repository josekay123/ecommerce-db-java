package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for database connection management
 */
public class DatabaseUtil {
    
    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/unisoft";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "kanombe12";
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    
    /**
     * Gets a database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL JDBC driver
            Class.forName(DRIVER_CLASS);
            
            // Establish and return the connection
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Please add mysql-connector-java to your classpath.", e);
        }
    }
    
    /**
     * Tests the database connection
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Closes a database connection safely
     * @param conn the connection to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Gets database connection details (without password for security)
     * @return String with connection info
     */
    public static String getConnectionInfo() {
        return "Database URL: " + URL + ", Username: " + USERNAME;
    }
}