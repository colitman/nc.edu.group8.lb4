package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionFactory;
import action.WrongCommandException;
import action.ActionException;
import action.HttpAction;

public class Utils {
	
	public static String perform(String action, HttpServletRequest request, HttpServletResponse response) throws WrongCommandException, ActionException {
		return ActionFactory.getInstance().build(action).perform(request, response);
	}
}