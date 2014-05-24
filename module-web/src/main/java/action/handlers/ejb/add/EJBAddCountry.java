package action.handlers.ejb.add;

import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import action.EJBAction;
import action.ActionException;

import hibernate.logic.Country;
import hibernate.logic.Region;
import hibernate.logic.City;
import hibernate.logic.University;

import beans.country.CountryHome;
import beans.region.RegionHome;
import beans.city.CityHome;
import beans.uni.UniversityHome;

public class EJBAddCountry implements EJBAction {
	
	public Object perform(Context context, Object entity, Object... args) throws ActionException {
		try {
			Object ref = context.lookup(COUNTRY_BEAN);
			
			CountryHome home = (CountryHome) PortableRemoteObject.narrow(ref, CountryHome.class);
				
			Country object = (Country) entity;
			
			home.create(object.getName(),
						object.getLanguage(),
						object.getCapital(),
						object.getPopulation(),
						object.getTimezone());
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
		return null;
	}
}