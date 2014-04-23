package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Locale;
import java.util.Properties;

import oracle.jdbc.pool.OracleDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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
			if(con == null || con.isClosed()) {
				Properties p = new Properties();
				p.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
				p.put(Context.PROVIDER_URL, "t3://127.0.0.1:7001");
				p.put(Context.SECURITY_PRINCIPAL, "admin");
				p.put(Context.SECURITY_CREDENTIALS, "admin33284");
				Context context = new InitialContext(p);
				
				DataSource source = (DataSource) context.lookup("jdbc/test");
				
				con = source.getConnection();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (NamingException ne) {
			ne.printStackTrace();
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
