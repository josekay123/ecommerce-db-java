package jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try {
	            Connection conn = DatabaseUtil.getConnection();
	            System.out.println("Database connection successful!");
	            conn.close();
	        } catch (SQLException e) {
	            System.out.println("Connection failed: " + e.getMessage());
	            e.printStackTrace();
	        }

	}

}
