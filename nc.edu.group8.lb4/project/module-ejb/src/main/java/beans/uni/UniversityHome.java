package beans.uni;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface UniversityHome extends EJBHome {
	public UniversityRemote create(Integer id, Integer pid, String name, int depCount, String www) throws RemoteException,   CreateException;
	public UniversityRemote findByPrimaryKey(Integer id) throws FinderException,   RemoteException;
	public Collection<UniversityRemote> findAll()   throws FinderException,   RemoteException;
}
