package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTool {
	
	private static DBTool instance = null;
	
	private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";
	
	private Connection con = null;
	
	private DBTool() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Can't find the JDBC driver: " + JDBC_DRIVER, e);
		}
	}
	
	public static DBTool getTool() {
		if(instance == null) {
			instance = new DBTool();
		}
		
		return instance;
	}
	
	public Connection getConnection() {
		try {
			if(con.isClosed() || con == null) {
				con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return con;
	}
	
	public int getNextIdFor(String tableName) {
		ResultSet rs = null;
		PreparedStatement stm = null;
		int ID = -1;
		getConnection();
		try {
			stm = con.prepareStatement("select max(ID) from ?");
			stm.setString(1, tableName);
			rs = stm.executeQuery();
			if(rs.next()) {
				ID = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stm.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
				
		}
		
		return (ID == -1)? ID: ID + 1;
	}
	
	public void releaseConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
