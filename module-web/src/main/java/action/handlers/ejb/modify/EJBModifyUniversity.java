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

public class EJBModifyUniversity implements EJBAction {
	
	public Object perform(Context context, Object entity, Object... args) throws ActionException {
		try {
			Object ref = context.lookup(UNIVERSITY_BEAN);
			
			UniversityHome home = (UniversityHome) PortableRemoteObject.narrow(ref, UniversityHome.class);
			
			University object = (University) entity;
			
			UniversityRemote remote = home.findByPrimaryKey(object.getID());
			
			remote.setParentID(object.getParentID());
			remote.setName(object.getName());
			remote.setDepartamentsCount(object.getDepartamentsCount());
			remote.setWWW(object.getWWW());
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
		return null;
	}
}