package util.gateway;

import java.util.*;
import java.sql.*;
import hibernate.dao.*;
import hibernate.logic.*;

import java.lang.reflect.ParameterizedType;

public class ListGateway<T extends Entity> implements Gateway<T> {
 
	private static int index = 1;
	private List<T> list = new ArrayList<T>();

	public void add(T entity) throws SQLException {
		entity.setID(index++);
		list.add(entity);
	}

	public void modify(T entity) throws SQLException {
		int id = entity.getID();
		if (get(null, id) != null) {
			remove(get(null, id));
			add(entity);
		} else {
			add(entity);
		}
	}	

	public T get(Class<T> className, int id) throws SQLException {
		for (T entity : list) {
			if (entity.getID() == id) {
				return entity;
			}
		}
		return null;
	}

	public Collection<T> getAll(Class className) throws SQLException {
		return list;
	}

	public Collection<T> getAllBy(Class className, int parentID) throws SQLException {
		Collection<T> collection = new ArrayList<T>();
		for (T entity : list) {
			if (entity.getParentID() == parentID) {
				collection.add(entity);
			}
		}
		return collection;
	}

	public void remove(T entity) throws SQLException {
		list.remove(entity);
	}

	public int size() {
		return list.size();
	}
}