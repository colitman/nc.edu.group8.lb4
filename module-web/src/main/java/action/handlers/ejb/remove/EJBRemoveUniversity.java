package action.handlers.ejb.remove;

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

public class EJBRemoveUniversity implements EJBAction {
	
	public Object perform(Context context, Object entity, Object... args) throws ActionException {
		try {
			Object ref = context.lookup(UNIVERSITY_BEAN);
			
			UniversityHome home = (UniversityHome) PortableRemoteObject.narrow(ref, UniversityHome.class);
			
			University object = (University) entity;
			
			home.remove(object.getID());
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
		return null;
	}
}