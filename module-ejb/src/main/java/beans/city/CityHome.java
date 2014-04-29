package beans.city;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface CityHome extends EJBHome {
	public CityRemote create(Integer pid, String name, int population, int square) throws RemoteException,   CreateException;
	public CityRemote findByPrimaryKey(Integer id) throws FinderException,   RemoteException;
	public Collection<CityRemote> findAll()   throws FinderException,   RemoteException;
}
