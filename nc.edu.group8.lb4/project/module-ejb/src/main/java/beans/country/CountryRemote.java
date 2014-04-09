package beans.country;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface CountryRemote extends EJBObject {
	public int getID() throws RemoteException;
	public void setName(String name) throws RemoteException;
	public String getName() throws RemoteException;
	public void setLanguage(String lang) throws RemoteException;
	public String getLanguage() throws RemoteException;
	public void setCapital(String capital) throws RemoteException;
	public String getCapital() throws RemoteException;
	public void setPopulation(int population) throws RemoteException;
	public int getPopulation() throws RemoteException;
	public void setTimezone(int tz) throws RemoteException;
	public int getTimezone() throws RemoteException;
}
