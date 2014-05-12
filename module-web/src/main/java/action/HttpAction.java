package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpAction {
	
	public String perform(HttpServletRequest request, HttpServletResponse response) throws ActionException;
}