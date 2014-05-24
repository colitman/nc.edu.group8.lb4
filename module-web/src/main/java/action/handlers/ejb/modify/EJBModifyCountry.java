package action.handlers.ejb.modify;

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

public class EJBModifyCountry implements EJBAction {
	
	public Object perform(Context context, Object entity, Object... args) throws ActionException {
		try {
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
		catch (Exception e) {
			throw new ActionException(e);
		}
		return null;
	}
}