package beans.uni;

import java.sql.SQLException;
import java.util.Collection;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

public class University implements EntityBean {
	private static final long serialVersionUID = 1L;
	private Integer parentID;
	private Integer ID;
	private String name;
	private int depCount;
	private String WWW;
	private EntityContext context;
	
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
			throw new EJBException("Loading failed: " + e.getMessage());
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
			throw new EJBException("Removing failed: " + e.getMessage());
		}
	}

	@Override
	public void ejbStore() {
		try {
			storeInstance();
		} catch (SQLException e) {
			throw new EJBException("Updating failed: " + e.getMessage());
		}
	}

	public void ejbPostCreate(Integer id, Integer pid, String name, int depCount, String www) {}
	
	public Integer ejbCreate(Integer id, Integer pid, String name, int depCount, String www) {
		try {
			createInstance(id, pid, name, depCount, www);
		} catch (Exception e) {
			throw new EJBException("Creation failed: " + e.getMessage());
		}
		
		this.ID = id;
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
			throw new EJBException("Find by PK failed: " + e.getMessage());
		}
	}

	public Collection<Integer> ejbFindAll() {
		try {
			return selectAll();
		} catch (SQLException e) {
			throw new EJBException("Find all failed: " + e.getMessage());
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
	
	private void createInstance(Integer id, Integer pid, String name, int depCount, String www) throws SQLException {
		// TODO Auto-generated method stub
	}
	
	private void loadInstance() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	private void removeInstance() throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	private void storeInstance() throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	private boolean selectByPK(Integer key) throws SQLException {
		// TODO Auto-generated method stub
		return true;
	}
	
	private Collection<Integer> selectAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
