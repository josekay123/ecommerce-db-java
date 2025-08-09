package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcExample {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
	
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unisoft","root" ,"kanombe12");
		
		System.out.println("connection created");
		
	}

}
