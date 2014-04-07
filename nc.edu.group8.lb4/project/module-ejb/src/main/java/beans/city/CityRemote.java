package beans.city;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface CityRemote extends EJBObject {
	public void setParentID(int parentId) throws RemoteException;
	public int getParentID() throws RemoteException;
	public int getID() throws RemoteException;
	public void setName(String name) throws RemoteException;
	public String getName() throws RemoteException;
	public void setPopulation(int population) throws RemoteException;
	public int getPopulation() throws RemoteException;
	public void setSquare(int square) throws RemoteException;
	public int getSquare() throws RemoteException;
}
