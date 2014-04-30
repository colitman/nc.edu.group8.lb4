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

import beans.city.*;
import beans.country.*;
import beans.region.*;
import beans.uni.*;

import hibernate.logic.*;

@Service
public class EJBBeansGateway<T> implements Gateway<T> {

	private static final String COUNTRY_BEAN = "java:global.project.module-ejb-1.0.CountryBean!beans.country.CountryHome";
	private static final String CITY_BEAN = "java:global.project.module-ejb-1.0.CityBean!beans.city.CityHome";
	private static final String REGION_BEAN = "java:global.project.module-ejb-1.0.RegionBean!beans.region.RegionHome";
	private static final String UNIVERSITY_BEAN = "java:global.project.module-ejb-1.0.UniversityBean!beans.uni.UniversityHome";
	private static final Logger logger = Logger.getLogger(EJBBeansGateway.class);
	private Context context = createContext();
	
	@Override
	public void add(T entity) throws SQLException {
		try {
			if (entity instanceof University) {
				Object ref = context.lookup(UNIVERSITY_BEAN);
				
				UniversityHome home = (UniversityHome) PortableRemoteObject.narrow(ref, UniversityHome.class);
				
				University object = (University) entity;
				
				home.create(object.getParentID(),
							object.getName(),
							object.getDepartamentsCount(),
							object.getWWW());
			}
			if (entity instanceof City) {
				Object ref = context.lookup(CITY_BEAN);
				
				CityHome home = (CityHome) PortableRemoteObject.narrow(ref, CityHome.class);
					
				City object = (City) entity;
				
				home.create(object.getParentID(),
							object.getName(),
							object.getPopulation(),
							object.getSquare());
			}
			if (entity instanceof Region) {
				Object ref = context.lookup(REGION_BEAN);
				
				RegionHome home = (RegionHome) PortableRemoteObject.narrow(ref, RegionHome.class);
					
				Region object = (Region) entity;
				
				home.create(object.getParentID(),
							object.getName(),
							object.getPopulation(),
							object.getSquare());
			}
			if (entity instanceof Country) {
				Object ref = context.lookup(COUNTRY_BEAN);
				
				CountryHome home = (CountryHome) PortableRemoteObject.narrow(ref, CountryHome.class);
					
				Country object = (Country) entity;
				
				home.create(object.getName(),
							object.getLanguage(),
							object.getCapital(),
							object.getPopulation(),
							object.getTimezone());
			}
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
			if (entity instanceof University) {
				Object ref = context.lookup(UNIVERSITY_BEAN);
				
				UniversityHome home = (UniversityHome) PortableRemoteObject.narrow(ref, UniversityHome.class);
				
				University object = (University) entity;
				
				UniversityRemote remote = home.findByPrimaryKey(object.getID());
				
				remote.setParentID(object.getParentID());
				remote.setName(object.getName());
				remote.setDepartamentsCount(object.getDepartamentsCount());
				remote.setWWW(object.getWWW());
			}
			if (entity instanceof City) {
				Object ref = context.lookup(CITY_BEAN);
				
				CityHome home = (CityHome) PortableRemoteObject.narrow(ref, CityHome.class);
				
				City object = (City) entity;
				
				CityRemote remote = home.findByPrimaryKey(object.getID());
				
				remote.setParentID(object.getParentID());
				remote.setName(object.getName());
				remote.setPopulation(object.getPopulation());
				remote.setSquare(object.getSquare());
			}
			if (entity instanceof Region) {
				Object ref = context.lookup(REGION_BEAN);
				
				RegionHome home = (RegionHome) PortableRemoteObject.narrow(ref, RegionHome.class);
				
				Region object = (Region) entity;
				
				RegionRemote remote = home.findByPrimaryKey(object.getID());
				
				remote.setParentID(object.getParentID());
				remote.setName(object.getName());
				remote.setPopulation(object.getPopulation());
				remote.setSquare(object.getSquare());
			}
			if (entity instanceof Country) {
				Object ref = context.lookup(COUNTRY_BEAN);
				
				CountryHome home = (CountryHome) PortableRemoteObject.narrow(ref, CountryHome.class);
				
				Country object = (Country) entity;
				
				CountryRemote remote = home.findByPrimaryKey(object.getID());
				
				remote.setName(object.getName());
				remote.setLanguage(object.getLanguage());
				remote.setCapital(object.getCapital());
				remote.setPopulation(object.getPopulation());
				remote.setTimezone(object.getTimezone());
			}
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
			if (className.getSimpleName().equals("University")) {
				Object ref = context.lookup(UNIVERSITY_BEAN);
				
				UniversityHome home = (UniversityHome) PortableRemoteObject.narrow(ref, UniversityHome.class);
				
				University object = new University();
				
				UniversityRemote remote = home.findByPrimaryKey(id);
				
				object.setID(remote.getID());
				object.setParentID(remote.getParentID());
				object.setName(remote.getName());
				object.setDepartamentsCount(remote.getDepartamentsCount());
				object.setWWW(remote.getWWW());
				
				return (T) object;
			}
			if (className.getSimpleName().equals("City")) {
				Object ref = context.lookup(CITY_BEAN);
				
				CityHome home = (CityHome) PortableRemoteObject.narrow(ref, CityHome.class);
				
				City object = new City();
				
				CityRemote remote = home.findByPrimaryKey(id);
				
				object.setID(remote.getID());
				object.setParentID(remote.getParentID());
				object.setName(remote.getName());
				object.setPopulation(remote.getPopulation());
				object.setSquare(remote.getSquare());
				
				return (T) object;
			}
			if (className.getSimpleName().equals("Region")) {
				Object ref = context.lookup(REGION_BEAN);
				
				RegionHome home = (RegionHome) PortableRemoteObject.narrow(ref, RegionHome.class);
				
				Region object = new Region();
				
				RegionRemote remote = home.findByPrimaryKey(id);
				
				object.setID(remote.getID());
				object.setParentID(remote.getParentID());
				object.setName(remote.getName());
				object.setPopulation(remote.getPopulation());
				object.setSquare(remote.getSquare());
				
				return (T) object;
			}
			if (className.getSimpleName().equals("Country")) {
				Object ref = context.lookup(COUNTRY_BEAN);
				
				CountryHome home = (CountryHome) PortableRemoteObject.narrow(ref, CountryHome.class);
				
				Country object = new Country();
				
				CountryRemote remote = home.findByPrimaryKey(id);
				
				object.setID(remote.getID());
				object.setName(remote.getName());
				object.setLanguage(remote.getLanguage());
				object.setCapital(remote.getCapital());
				object.setPopulation(remote.getPopulation());
				object.setTimezone(remote.getTimezone());
				
				return (T) object;
			}
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
			if (className.getSimpleName().equals("University")) {
				Object ref = context.lookup(UNIVERSITY_BEAN);
				
				UniversityHome home = (UniversityHome) PortableRemoteObject.narrow(ref, UniversityHome.class);
				
				Collection<University> objectList = new ArrayList<University>();
				
				Collection<UniversityRemote> remoteList = home.findAll();
				
				for (UniversityRemote remote : remoteList) {
					University object = new University();
					object.setID(remote.getID());
					object.setParentID(remote.getParentID());
					object.setName(remote.getName());
					object.setDepartamentsCount(remote.getDepartamentsCount());
					object.setWWW(remote.getWWW());
				
					objectList.add(object);
				}
				
				return (Collection<T>) objectList;
			}
			if (className.getSimpleName().equals("City")) {
				Object ref = context.lookup(CITY_BEAN);
				
				CityHome home = (CityHome) PortableRemoteObject.narrow(ref, CityHome.class);
				
				Collection<City> objectList = new ArrayList<City>();
				
				Collection<CityRemote> remoteList = home.findAll();
				
				for (CityRemote remote : remoteList) {
					City object = new City();
					object.setID(remote.getID());
					object.setParentID(remote.getParentID());
					object.setName(remote.getName());
					object.setPopulation(remote.getPopulation());
					object.setSquare(remote.getSquare());
				
					objectList.add(object);
				}
				
				return (Collection<T>) objectList;
			}
			if (className.getSimpleName().equals("Region")) {
				Object ref = context.lookup(REGION_BEAN);
				
				RegionHome home = (RegionHome) PortableRemoteObject.narrow(ref, RegionHome.class);
				
				Collection<Region> objectList = new ArrayList<Region>();
				
				Collection<RegionRemote> remoteList = home.findAll();
				
				for (RegionRemote remote : remoteList) {
					Region object = new Region();
					object.setID(remote.getID());
					object.setParentID(remote.getParentID());
					object.setName(remote.getName());
					object.setPopulation(remote.getPopulation());
					object.setSquare(remote.getSquare());
				
					objectList.add(object);
				}
				
				return (Collection<T>) objectList;
			}
			if (className.getSimpleName().equals("Country")) {
				Object ref = context.lookup(COUNTRY_BEAN);
				
				CountryHome home = (CountryHome) PortableRemoteObject.narrow(ref, CountryHome.class);
				
				Collection<Country> objectList = new ArrayList<Country>();
				
				Collection<CountryRemote> remoteList = home.findAll();
				
				for (CountryRemote remote : remoteList) {
					Country object = new Country();
					object.setID(remote.getID());
					object.setName(remote.getName());
					object.setLanguage(remote.getLanguage());
					object.setCapital(remote.getCapital());
					object.setPopulation(remote.getPopulation());
					object.setTimezone(remote.getTimezone());
				
					objectList.add(object);
				}
				
				return (Collection<T>) objectList;
			}
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
	public Collection<T> getAllBy(Class className, int parentID)
			throws SQLException {
		try {
			if (className.getSimpleName().equals("University")) {
				Object ref = context.lookup(UNIVERSITY_BEAN);
				
				UniversityHome home = (UniversityHome) PortableRemoteObject.narrow(ref, UniversityHome.class);
				
				Collection<University> objectList = new ArrayList<University>();
				
				Collection<UniversityRemote> remoteList = home.findAll();
				
				for (UniversityRemote remote : remoteList) {
					if (remote.getParentID() == parentID) {
						University object = new University();
						object.setID(remote.getID());
						object.setParentID(remote.getParentID());
						object.setName(remote.getName());
						object.setDepartamentsCount(remote.getDepartamentsCount());
						object.setWWW(remote.getWWW());
					
						objectList.add(object);
					}
				}
				return (Collection<T>) objectList;
			}
			if (className.getSimpleName().equals("City")) {
				Object ref = context.lookup(CITY_BEAN);
				
				CityHome home = (CityHome) PortableRemoteObject.narrow(ref, CityHome.class);
				
				Collection<City> objectList = new ArrayList<City>();
				
				Collection<CityRemote> remoteList = home.findAll();
				
				for (CityRemote remote : remoteList) {
					if (remote.getParentID() == parentID) {
						City object = new City();
						object.setID(remote.getID());
						object.setParentID(remote.getParentID());
						object.setName(remote.getName());
						object.setPopulation(remote.getPopulation());
						object.setSquare(remote.getSquare());
					
						objectList.add(object);
					}
				}
				return (Collection<T>) objectList;
			}
			if (className.getSimpleName().equals("Region")) {
				Object ref = context.lookup(REGION_BEAN);
				
				RegionHome home = (RegionHome) PortableRemoteObject.narrow(ref, RegionHome.class);
				
				Collection<Region> objectList = new ArrayList<Region>();
				
				Collection<RegionRemote> remoteList = home.findAll();
				
				for (RegionRemote remote : remoteList) {
					if (remote.getParentID() == parentID) {
						Region object = new Region();
						object.setID(remote.getID());
						object.setParentID(remote.getParentID());
						object.setName(remote.getName());
						object.setPopulation(remote.getPopulation());
						object.setSquare(remote.getSquare());
					
						objectList.add(object);
					}
				}
				return (Collection<T>) objectList;
			}
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
			if (entity instanceof University) {
				Object ref = context.lookup(UNIVERSITY_BEAN);
				
				UniversityHome home = (UniversityHome) PortableRemoteObject.narrow(ref, UniversityHome.class);
				
				University object = (University) entity;
				
				home.remove(object.getID());
			}
			if (entity instanceof City) {
				Object ref = context.lookup(CITY_BEAN);
				
				CityHome home = (CityHome) PortableRemoteObject.narrow(ref, CityHome.class);
				
				City object = (City) entity;
				
				home.remove(object.getID());
			}
			if (entity instanceof Region) {
				Object ref = context.lookup(REGION_BEAN);
				
				RegionHome home = (RegionHome) PortableRemoteObject.narrow(ref, RegionHome.class);
				
				Region object = (Region) entity;
				
				home.remove(object.getID());
			}
			if (entity instanceof Country) {
				Object ref = context.lookup(COUNTRY_BEAN);
				
				CountryHome home = (CountryHome) PortableRemoteObject.narrow(ref, CountryHome.class);

				Country object = (Country) entity;
				
				home.remove(object.getID());
			}
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