package hibernate.dao;

import java.util.Map;
import java.util.HashMap;

import hibernate.logic.Country;
import hibernate.logic.Region;
import hibernate.logic.City;
import hibernate.logic.University;

import action.EJBAction;
import action.handlers.ejb.add.EJBAddCountry;
import action.handlers.ejb.add.EJBAddRegion;
import action.handlers.ejb.add.EJBAddCity;
import action.handlers.ejb.add.EJBAddUniversity;
import action.handlers.ejb.get.EJBGetCountry;
import action.handlers.ejb.get.EJBGetRegion;
import action.handlers.ejb.get.EJBGetCity;
import action.handlers.ejb.get.EJBGetUniversity;
import action.handlers.ejb.get.EJBGetAllCountry;
import action.handlers.ejb.get.EJBGetAllRegion;
import action.handlers.ejb.get.EJBGetAllCity;
import action.handlers.ejb.get.EJBGetAllUniversity;
import action.handlers.ejb.get.EJBGetAllByRegion;
import action.handlers.ejb.get.EJBGetAllByCity;
import action.handlers.ejb.get.EJBGetAllByUniversity;
import action.handlers.ejb.modify.EJBModifyCountry;
import action.handlers.ejb.modify.EJBModifyRegion;
import action.handlers.ejb.modify.EJBModifyCity;
import action.handlers.ejb.modify.EJBModifyUniversity;
import action.handlers.ejb.remove.EJBRemoveCountry;
import action.handlers.ejb.remove.EJBRemoveRegion;
import action.handlers.ejb.remove.EJBRemoveCity;
import action.handlers.ejb.remove.EJBRemoveUniversity;

import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class EJBBeansFactory {

	private static final Logger logger = Logger.getLogger(EJBBeansFactory.class);
	
	private static EJBBeansFactory instance;
	private Map<String, Map<Class, EJBAction>> map = init();
	
	private EJBBeansFactory() {} 
	
	public static EJBBeansFactory getInstance() {
		if (instance == null) {
			instance = new EJBBeansFactory();
		}
		return instance;
	}
	
	public EJBAction getAction(String action, Class someClass) {	
		logger.info("Getting action " + action + " for " + someClass.getSimpleName());
		
		return map.get(action).get(someClass);
	}
	
	private Map<String, Map<Class, EJBAction>> init() {
		try {
			logger.info("Initializing EJBBeansFactory");
			
			Map<String, Map<Class, EJBAction>> mainMap = new HashMap<String, Map<Class, EJBAction>>();
			
			Map<Class, EJBAction> map = new HashMap<Class, EJBAction>();
			
			map.put(Country.class, EJBAddCountry.class.newInstance());
			map.put(Region.class, EJBAddRegion.class.newInstance());
			map.put(City.class, EJBAddCity.class.newInstance());
			map.put(University.class, EJBAddUniversity.class.newInstance());
			
			mainMap.put("Add", map);
			
			map = new HashMap<Class, EJBAction>();
			
			map.put(Country.class, EJBRemoveCountry.class.newInstance());
			map.put(Region.class, EJBRemoveRegion.class.newInstance());
			map.put(City.class, EJBRemoveCity.class.newInstance());
			map.put(University.class, EJBRemoveUniversity.class.newInstance());
			
			mainMap.put("Remove", map);
			
			map = new HashMap<Class, EJBAction>();
			
			map.put(Country.class, EJBModifyCountry.class.newInstance());
			map.put(Region.class, EJBModifyRegion.class.newInstance());
			map.put(City.class, EJBModifyCity.class.newInstance());
			map.put(University.class, EJBModifyUniversity.class.newInstance());
			
			mainMap.put("Modify", map);
			
			map = new HashMap<Class, EJBAction>();
			
			map.put(Country.class, EJBGetCountry.class.newInstance());
			map.put(Region.class, EJBGetRegion.class.newInstance());
			map.put(City.class, EJBGetCity.class.newInstance());
			map.put(University.class, EJBGetUniversity.class.newInstance());
			
			mainMap.put("Get", map);
			
			map = new HashMap<Class, EJBAction>();
			
			map.put(Country.class, EJBGetAllCountry.class.newInstance());
			map.put(Region.class, EJBGetAllRegion.class.newInstance());
			map.put(City.class, EJBGetAllCity.class.newInstance());
			map.put(University.class, EJBGetAllUniversity.class.newInstance());
			
			mainMap.put("GetAll", map);
			
			map = new HashMap<Class, EJBAction>();
			
			map.put(University.class, EJBGetAllByUniversity.class.newInstance());
			map.put(Region.class, EJBGetAllByRegion.class.newInstance());
			map.put(City.class, EJBGetAllByCity.class.newInstance());
			
			mainMap.put("GetAllBy", map);
			
			return mainMap;
		}
		catch (Exception e) {
			logger.error("Exception occured in EJBBeansFactory", e);
		}
		return null;
	}
}