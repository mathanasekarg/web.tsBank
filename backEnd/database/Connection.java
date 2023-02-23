package database;

import java.sql.*;

public class Connection {
	private static java.sql.Connection connection = null;
	public Connection (){
		connection = this.connection();
	}
	public static java.sql.Connection getConnection() {
		if(connection == null) new Connection();
		return connection;
	}
	private java.sql.Connection connection() {
		String url = "jdbc:mysql://localhost:3306/tsbank?allowMultiQueries=true"; // table details
		String username = "root"; // MySQL credentials
		String password = "root";
		java.sql.Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
			System.out.println("Database Connection Established successfully...!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
