package hibernate.dao;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import beans.city.CityHome;
import beans.city.CityRemote;
import beans.country.CountryHome;
import beans.country.CountryRemote;
import beans.region.RegionHome;
import beans.region.RegionRemote;
import beans.uni.UniversityHome;
import beans.uni.UniversityRemote;

import hibernate.logic.City;
import hibernate.logic.Country;
import hibernate.logic.Region;
import hibernate.logic.University;

import action.EJBAction;

@Service
public class EJBBeansGateway<T> implements Gateway<T> {

	private static final Logger logger = Logger.getLogger(EJBBeansGateway.class);
	private Context context = createContext();
	
	@Override
	public void add(T entity) throws SQLException {		
		try {
			EJBBeansFactory.getInstance().getAction("Add", entity.getClass()).perform(context, entity);
		}
		catch (NamingException ne) {
			logger.error("Bean has not be found. Ensure that bean name is correct", ne);
		}
		catch (RemoteException re) {
			logger.error("Connection problems. Ensure that weblogic is running", re);
		}
		catch (Exception e) {
			logger.error("Exception occured in EJBBeansGateway", e);
		}
	}

	@Override
	public void modify(T entity) throws SQLException {
		try {
			EJBBeansFactory.getInstance().getAction("Modify", entity.getClass()).perform(context, entity);
		}
		catch (NamingException ne) {
			logger.info("Bean has not be found. Ensure that bean name is correct", ne);
		}
		catch (RemoteException re) {
			logger.error("Connection problems. Ensure that weblogic is running", re);
		}
		catch (Exception e) {
			logger.error("Exception occured in EJBBeansGateway", e);
		}
	}

	@Override
	public T get(Class<T> className, int id) throws SQLException {
		try {
			return (T) EJBBeansFactory.getInstance().getAction("Get", className).perform(context, null, new Integer(id));
		}
		catch (NamingException ne) {
			logger.info("Bean has not be found. Ensure that bean name is correct", ne);
		}
		catch (RemoteException re) {
			logger.error("Connection problems. Ensure that weblogic is running", re);
		}
		catch (Exception e) {
			logger.error("Exception occured in EJBBeansGateway", e);
		}
		return null;
	}

	@Override
	public Collection<T> getAll(Class className) throws SQLException {
		try {
			return (Collection<T>) EJBBeansFactory.getInstance().getAction("GetAll", className).perform(context, null);
		}
		catch (NamingException ne) {
			logger.info("Bean has not be found. Ensure that bean name is correct", ne);
		}
		catch (RemoteException re) {
			logger.error("Connection problems. Ensure that weblogic is running", re);
		}
		catch (Exception e) {
			logger.error("Exception occured in EJBBeansGateway", e);
		}
		return null;
	}

	@Override
	public Collection<T> getAllBy(Class className, int parentID) throws SQLException {
		try {
			return (Collection<T>) EJBBeansFactory.getInstance().getAction("GetAllBy", className).perform(context, null, new Integer(parentID));
		}
		catch (NamingException ne) {
			logger.info("Bean has not be found. Ensure that bean name is correct", ne);
		}
		catch (RemoteException re) {
			logger.error("Connection problems. Ensure that weblogic is running", re);
		}
		catch (Exception e) {
			logger.error("Exception occured in EJBBeansGateway", e);
		}
		return null;
	}

	@Override
	public void remove(T entity) throws SQLException {
		try {
			EJBBeansFactory.getInstance().getAction("Remove", entity.getClass()).perform(context, entity);
		}
		catch (NamingException ne) {
			logger.info("Bean has not be found. Ensure that bean name is correct", ne);
		}
		catch (RemoteException re) {
			logger.error("Connection problems. Ensure that weblogic is running", re);
		}
		catch (Exception e) {
			logger.error("Exception occured in EJBBeansGateway", e);
		}
	}
	
	private Context createContext() {
		try {
			Properties p = new Properties();
			p.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			p.put(Context.PROVIDER_URL, "t3://127.0.0.1:7001");
			p.put(Context.SECURITY_PRINCIPAL, "admin");
			p.put(Context.SECURITY_CREDENTIALS, "admin33284");
			return new InitialContext(p);
		}
		catch (NamingException e) {
			logger.error("Connection problems. Please ensure that weblogic is already run and check"
					+ " you provider url, login and password", e);
		}
		return null;
	}
	
}