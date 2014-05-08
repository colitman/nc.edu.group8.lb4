package beans.uni;

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

public class UniversityBean implements EntityBean {
	private static final long serialVersionUID = 1L;
	private Integer parentID;
	private Integer ID;
	private String name;
	private int depCount;
	private String WWW;
	private EntityContext context;
	
	private static final String TABLE_NAME = "UNIVERSITY";
	
	public void setParentID(int parentId) {
		this.parentID = parentId;
	}
	
	public int getParentID() {
		return parentID;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDepartamentsCount(int count) {
		this.depCount = count;
	}
	
	public int getDepartamentsCount() {
		return depCount;
	}
	
	public void setWWW(String www) {
		this.WWW = www;
	}
	
	public String getWWW() {
		return WWW;
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

	public void ejbPostCreate(Integer pid, String name, int depCount, String www) {}
	
	public Integer ejbCreate(Integer pid, String name, int depCount, String www) {
		try {
			this.ID = createInstance(pid, name, depCount, www);
		} catch (Exception e) {
			throw new EJBException("Creation failed: " + e.getMessage(), e);
		}
		
		this.parentID = pid;
		this.name = name;
		this.WWW = www;
		this.depCount = depCount;
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
	
	private Integer createInstance(Integer pid, String name, int depCount, String www) throws SQLException {

		Connection con = null;
		PreparedStatement stm = null;
		
		try {
			this.ID = Integer.valueOf(DBTool.getTool().getNextIdFor(TABLE_NAME));
			
			if(ID == -1) {
				DBTool.getTool().releaseConnection();
				throw new SQLException("Invalid table name or DB access problem - " + TABLE_NAME);
			}
			
			con = DBTool.getTool().getConnection();
			stm = con.prepareStatement("insert into " + TABLE_NAME + " (PARENT_ID, ID, NAME, DEPTS_COUNT, WWW) values (?, ?, ?, ?, ?)");
			stm.setInt(1, pid);
			stm.setInt(2, ID);
			stm.setString(3, name);
			stm.setInt(4, depCount);
			stm.setString(5, www);
			
			stm.executeUpdate();
			
			stm.close();
		}
		finally {
			DBTool.getTool().releaseConnection();
		}
		return ID;
	}
	
	private void loadInstance() throws SQLException {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		try {
			this.ID = (Integer) context.getPrimaryKey();
			
			con = DBTool.getTool().getConnection();
			stm = con.prepareStatement("select PARENT_ID, NAME, DEPTS_COUNT, WWW from " + TABLE_NAME + " where ID = ?");
			stm.setInt(1, ID);
			rs = stm.executeQuery();
			if(rs.next()) {
				this.parentID = rs.getInt(1);
				this.name = rs.getString(2);
				this.depCount = rs.getInt(3);
				this.WWW = rs.getString(4);
			} else {
				throw new NoSuchEntityException("University with ID = " + ID + " is not found in the database");
			}
			
			rs.close();
			stm.close();
		}
		finally {
			DBTool.getTool().releaseConnection();
		}
	}

	private void removeInstance() throws SQLException {
		
		Connection con = null;
		PreparedStatement stm = null;

		try {
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
		Connection con = null;
		PreparedStatement stm = null;
		
		try {
			con = DBTool.getTool().getConnection();
			stm = con.prepareStatement("update " + TABLE_NAME + " set PARENT_ID = ?, "
																	+ "NAME = ?, "
																	+ "DEPTS_COUNT = ?, "
																	+ "WWW = ? "
																+ "where ID = ?");
			
			stm.setInt(1, parentID);
			stm.setString(2, name);
			stm.setInt(3, depCount);
			stm.setString(4, WWW);
			stm.setInt(5, ID);
			
			int rowsUpd = stm.executeUpdate();
			
			
			
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
