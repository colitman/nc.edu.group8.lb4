package action.handlers.region;

import static org.junit.Assert.*;

import org.junit.Test;

import action.ActionException;
import action.GatewayResolver;

import hibernate.logic.Region;

import util.Utils;
import util.ServletRequestSkeleton;
import util.ServletResponseSkeleton;
import util.gateway.ListGateway;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveRegionTest {

	@Test(expected = IllegalArgumentException.class)
	public void nullTest() throws Exception {
		Utils.perform("removeRegion", null, null);
	}

	@Test(expected = ActionException.class)
	public void actionExceptionTest() throws Exception {
		ListGateway<Region> gateway = new ListGateway<Region>();
	
		GatewayResolver.setGateway(gateway);

		HttpServletRequest request = new ServletRequestSkeleton() {
			@Override
			public String getParameter(String name) {
				switch (name) {
					case "id" : return "bla";
					case "parent_id" : return "zaza";
				}
				return null;
			}
		};

		Utils.perform("removeRegion", request, new ServletResponseSkeleton());
	}

	@Test
	public void logicTest() throws Exception {
		ListGateway<Region> gateway = new ListGateway<Region>();

		GatewayResolver.setGateway(gateway);

		HttpServletRequest request = new ServletRequestSkeleton() {
			@Override
			public String getParameter(String name) {
				switch (name) {
					case "name" : return "skeleton";
					case "population" : return "1000";
					case "square" : return "0";
					case "id" : return "35";
					case "parent_id" : return "3";
				}
				return null;
			}
		};
		HttpServletResponse response = new ServletResponseSkeleton();

		assertEquals(0, gateway.size());

		Utils.perform("addRegion", request, response);
		Utils.perform("addRegion", request, response);
		Utils.perform("addRegion", request, response);

		assertEquals(3, gateway.size());

		Utils.perform("removeRegion", request, response);
		
		assertEquals(2, gateway.size());
	}

	@Test
	public void forwardTest() throws Exception {
		ListGateway<Region> gateway = new ListGateway<Region>();

		GatewayResolver.setGateway(gateway);

		HttpServletRequest request = new ServletRequestSkeleton() {
			@Override
			public String getParameter(String name) {
				switch (name) {
					case "name" : return "skeleton";
					case "population" : return "1000";
					case "square" : return "0";
					case "id" : return "1";
					case "parent_id" : return "3";
				}
				return null;
			}
		};
		
		HttpServletResponse response = new ServletResponseSkeleton();

		Utils.perform("addRegion", request, response);	

		String url = Utils.perform("removeRegion", request, response);
		
		if (!url.contains("showAllRegion")) {
			fail("forward must contains <showAllRegion>"); 
		}
	}
}