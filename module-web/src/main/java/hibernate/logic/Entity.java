package hibernate.logic;

public interface Entity {
	
	public void setID(int id);
	public int getID();
	public void setParentID(int id);
	public int getParentID();
}