package beans.uni;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface UniversityRemote extends EJBObject {
	public void setParentID(int parentId) throws RemoteException;
	public int getParentID() throws RemoteException;
	public int getID() throws RemoteException;
	public void setName(String name) throws RemoteException;
	public String getName() throws RemoteException;
	public void setDepartamentsCount(int count) throws RemoteException;
	public int getDepartamentsCount() throws RemoteException;
	public void setWWW(String www) throws RemoteException;
	public String getWWW() throws RemoteException;
}
