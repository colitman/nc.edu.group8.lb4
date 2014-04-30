package beans.country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.NoSuchEntityException;

import util.DBTool;

public class CountryBean implements EntityBean {
	private static final long serialVersionUID = 1L;
	private Integer ID;
	private String name;
	private String lang;
	private String capital;
	private int population;
	private int timezone;
	private EntityContext context;
	
	private static final String TABLE_NAME = "COUNTRY";
	
	public int getID() {
		return ID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPopulation(int population) {
		this.population = population;
	}
	
	public int getPopulation() {
		return population;
	}
	
	public void setLanguage(String lang) {
		this.lang = lang;
	}
	
	public String getLanguage() {
		return lang;
	}
	
	public void setCapital(String capital) {
		this.capital = capital;
	}
	
	public String getCapital() {
		return capital;
	}
	
	public void setTimezone(int tz) {
		this.timezone = tz;
	}
	
	public int getTimezone() {
		return timezone;
	}

	@Override
	public void ejbActivate() {
		ID = (Integer) context.getPrimaryKey();
	}

	@Override
	public void ejbLoad() {
		try {
			loadInstance();
		} catch (SQLException e) {
			throw new EJBException("Loading failed: " + e.getMessage(), e);
		}
	}

	@Override
	public void ejbPassivate() {
		ID = null;
	}

	@Override
	public void ejbRemove() {
		try {
			removeInstance();
		} catch (SQLException e) {
			throw new EJBException("Removing failed: " + e.getMessage(), e);
		}
	}

	@Override
	public void ejbStore() {
		try {
			storeInstance();
		} catch (SQLException e) {
			throw new EJBException("Updating failed: " + e.getMessage(), e);
		}
	}

	public void ejbPostCreate(String name, String lang, String capital, int population, int timezone) {}
	
	public Integer ejbCreate(String name, String lang, String capital, int population, int timezone) {
		try {
			this.ID = createInstance(name, lang, capital, population, timezone);
		} catch (Exception e) {
			throw new EJBException("Creation failed: " + e.getMessage(), e);
		}
		
		this.name = name;
		this.lang = lang;
		this.capital = capital;
		this.population = population;
		this.timezone = timezone;
		return ID;
	}

	public Integer ejbFindByPrimaryKey(Integer key) {
		try {
			if(selectByPK(key)) {
				return key;
			} else {
				return -1;
			}
		} catch (SQLException e) {
			throw new EJBException("Find by PK failed: " + e.getMessage(), e);
		}
	}

	public Collection<Integer> ejbFindAll() {
		try {
			return selectAll();
		} catch (SQLException e) {
			throw new EJBException("Find all failed: " + e.getMessage(), e);
		}
	}

	@Override
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	@Override
	public void unsetEntityContext() {
		context = null;
	}
	
	private Integer createInstance(String name, String lang, String capital, int population, int timezone) throws SQLException {

		Connection con = null;
		PreparedStatement stm = null;
		
		try {
			this.ID = Integer.valueOf(DBTool.getTool().getNextIdFor(TABLE_NAME));
			
			if(ID == -1) {
				DBTool.getTool().releaseConnection();
				throw new SQLException("Invalid table name or DB access problem - " + TABLE_NAME);
			}
			
			con = DBTool.getTool().getConnection();
			
			stm = con.prepareStatement("insert into " + TABLE_NAME + " values (?, ?, ?, ?, ?, ?)");
			stm.setInt(1, ID);
			stm.setString(2, name);
			stm.setString(3, lang);
			stm.setString(4, capital);
			stm.setInt(5, population);
			stm.setInt(6, timezone);
			
			stm.executeUpdate();
			
			stm.close();
		}
		finally {
			DBTool.getTool().releaseConnection();
		}
		return ID;
	}
	
	private void loadInstance() throws SQLException {
		try {
			Connection con = null;
			PreparedStatement stm = null;
			ResultSet rs = null;
			
			this.ID = (Integer) context.getPrimaryKey();
			
			con = DBTool.getTool().getConnection();
			stm = con.prepareStatement("select NAME, LANG, CAPITAL, POPULATION, TIMEZONE from " + TABLE_NAME + " where ID = ?");
			stm.setInt(1, ID);
			rs = stm.executeQuery();
			if(rs.next()) {
				this.name = rs.getString(1);
				this.lang = rs.getString(2);
				this.capital = rs.getString(3);
				this.population = rs.getInt(4);
				this.timezone = rs.getInt(5);
			} else {
				throw new NoSuchEntityException("Country with ID = " + ID + " is not found in the database");
			}
			
			rs.close();
			stm.close();
		}
		finally {
			DBTool.getTool().releaseConnection();
		}
	}

	private void removeInstance() throws SQLException {
		try {
			Connection con = null;
			PreparedStatement stm = null;
			
			con = DBTool.getTool().getConnection();
			stm = con.prepareStatement("delete from " + TABLE_NAME + " where ID = ?");
			stm.setInt(1, ID);
			stm.executeUpdate();
			
			stm.close();
		}
		finally {
			DBTool.getTool().releaseConnection();
		}
	}
	
	private void storeInstance() throws SQLException {
		try {
			Connection con = null;
			PreparedStatement stm = null;
			
			con = DBTool.getTool().getConnection();
			stm = con.prepareStatement("update " + TABLE_NAME + " set NAME = ?, "
																	+ "LANG = ?, "
																	+ "CAPITAL = ?, "
																	+ "POPULATION = ?, "
																	+ "TIMEZONE = ? "
																+ "where ID = ?");
			
			stm.setString(1, name);
			stm.setString(2, lang);
			stm.setString(3, capital);
			stm.setInt(4, population);
			stm.setInt(5, timezone);
			stm.setInt(6, ID);
			
			int rowsUpd = stm.executeUpdate();
			
			if(rowsUpd == 0) {
				throw new EJBException("Updating country with ID = " + ID + " failed.");
			}
			
			stm.close();
		}
		finally {
			DBTool.getTool().releaseConnection();
		}
	}
	
	private boolean selectByPK(Integer key) throws SQLException {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		boolean exists = false;
		
		try {
			con = DBTool.getTool().getConnection();
			stm = con.prepareStatement("select ID from " + TABLE_NAME + " where ID = ?");
			stm.setInt(1, key);
			rs = stm.executeQuery();
			exists = rs.next();
			
			rs.close();
			stm.close();
		}
		finally {
			DBTool.getTool().releaseConnection();
		}
		return exists;
	}
	
	private Collection<Integer> selectAll() throws SQLException {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		Collection<Integer> list = new ArrayList<Integer>();
		
		try {
			con = DBTool.getTool().getConnection();
			stm = con.prepareStatement("select ID from " + TABLE_NAME);
			rs = stm.executeQuery();
			
			while(rs.next()) {
				list.add(rs.getInt(1));
			}
			
			rs.close();
			stm.close();
		} 
		finally {
			DBTool.getTool().releaseConnection();
		}
		return list;
	}
}
