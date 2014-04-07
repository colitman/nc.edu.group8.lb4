package beans.country;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface CountryHome extends EJBHome {
	public CountryRemote create(String name, String lang, String capital, int population, int timezone) throws RemoteException,   CreateException;
	public CountryRemote findByPrimaryKey(Integer id) throws FinderException,   RemoteException;
	public Collection<CountryRemote> findAll()   throws FinderException,   RemoteException;
}
