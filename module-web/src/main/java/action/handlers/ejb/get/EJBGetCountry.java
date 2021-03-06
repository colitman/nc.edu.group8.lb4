package action.handlers.ejb.get;

import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import action.EJBAction;
import action.ActionException;

import hibernate.logic.Country;
import hibernate.logic.Region;
import hibernate.logic.City;
import hibernate.logic.University;

import beans.country.CountryHome;
import beans.country.CountryRemote;
import beans.region.RegionHome;
import beans.region.RegionRemote;
import beans.city.CityHome;
import beans.city.CityRemote;
import beans.uni.UniversityHome;
import beans.uni.UniversityRemote;

public class EJBGetCountry implements EJBAction {
	
	public Object perform(Context context, Object entity, Object... args) throws ActionException {
		try {
			Object ref = context.lookup(COUNTRY_BEAN);
			
			CountryHome home = (CountryHome) PortableRemoteObject.narrow(ref, CountryHome.class);
			
			Country object = new Country();
			
			Integer id = (Integer) args[0];
			
			CountryRemote remote = home.findByPrimaryKey(id);
			
			object.setID(remote.getID());
			object.setName(remote.getName());
			object.setLanguage(remote.getLanguage());
			object.setCapital(remote.getCapital());
			object.setPopulation(remote.getPopulation());
			object.setTimezone(remote.getTimezone());
			
			return object;
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}
}