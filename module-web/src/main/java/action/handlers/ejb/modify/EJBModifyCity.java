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

public class EJBModifyCity implements EJBAction {
	
	public Object perform(Context context, Object entity, Object... args) throws ActionException {
		try {
			Object ref = context.lookup(CITY_BEAN);
			
			CityHome home = (CityHome) PortableRemoteObject.narrow(ref, CityHome.class);
			
			City object = (City) entity;
			
			CityRemote remote = home.findByPrimaryKey(object.getID());
			
			remote.setParentID(object.getParentID());
			remote.setName(object.getName());
			remote.setPopulation(object.getPopulation());
			remote.setSquare(object.getSquare());
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
		return null;
	}
}