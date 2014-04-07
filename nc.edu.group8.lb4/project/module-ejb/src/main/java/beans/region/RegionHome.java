package beans.region;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface RegionHome extends EJBHome {
	public RegionRemote create(Integer pid, String name, int population, int square) throws RemoteException,   CreateException;
	public RegionRemote findByPrimaryKey(Integer id) throws FinderException,   RemoteException;
	public Collection<RegionRemote> findAll()   throws FinderException,   RemoteException;
}
