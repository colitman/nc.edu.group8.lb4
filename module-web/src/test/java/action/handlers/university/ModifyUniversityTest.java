package action.handlers.university;

import static org.junit.Assert.*;

import org.junit.Test;

import action.ActionException;
import action.GatewayResolver;

import hibernate.logic.University;

import util.Utils;
import util.ServletRequestSkeleton;
import util.ServletResponseSkeleton;
import util.gateway.ListGateway;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModifyUniversityTest {

	@Test(expected = IllegalArgumentException.class)
	public void nullTest() throws Exception {
		Utils.perform("modifyUniversity", null, null);
	}

	@Test(expected = ActionException.class)
	public void actionExceptionTest() throws Exception {
		ListGateway<University> gateway = new ListGateway<University>();
	
		GatewayResolver.setGateway(gateway);

		HttpServletRequest request = new ServletRequestSkeleton() {
			@Override
			public String getParameter(String name) {
				switch (name) {
					case "name" : return "skeleton";
					case "departs_count" : return "ZZZ";
					case "www" : return "zzz";
					case "parent_id" : return "zaza";
				}
				return null;
			}
		};
		HttpServletResponse response = new ServletResponseSkeleton();

		Utils.perform("modifyUniversity", request, response);
	}

	@Test
	public void logicTest() throws Exception {
		ListGateway<University> gateway = new ListGateway<University>();

		GatewayResolver.setGateway(gateway);

		HttpServletRequest request = new ServletRequestSkeleton() {
			@Override
			public String getParameter(String name) {
				switch (name) {	
					case "id" : return "45";
					case "name" : return "skeleton";
					case "departs_count" : return "10";
					case "www" : return "zzz";
					case "parent_id" : return "1";
				}
				return null;
			}
		};
		HttpServletResponse response = new ServletResponseSkeleton();

		assertEquals(0, gateway.size());

		Utils.perform("addUniversity", request, response);
		Utils.perform("addUniversity", request, response);
		Utils.perform("addUniversity", request, response);

		assertEquals(3, gateway.size());

		Utils.perform("modifyUniversity", request, response);

		assertEquals(3, gateway.size());
	}

	@Test
	public void forwardTest() throws Exception {
		ListGateway<University> gateway = new ListGateway<University>();

		GatewayResolver.setGateway(gateway);

		HttpServletRequest request = new ServletRequestSkeleton() {
			@Override
			public String getParameter(String name) {
				switch (name) {
					case "id" : return "1";
					case "name" : return "skeleton";
					case "departs_count" : return "10";
					case "www" : return "zzz";
					case "parent_id" : return "1";
				}
				return null;
			}
		};
		
		String url = Utils.perform("addUniversity", request, new ServletResponseSkeleton());
		
		if (!url.contains("showAllUniversity")) {
			fail("forward must contains <showAllUniversity>"); 
		}
	}
}