package kudewe.reports.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kudewe.reports.metadata.MenuItemDefinition;
import kudewe.reports.services.OlapService;

import mondrian.olap.Result;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


public class OlapMenuDefinitionController extends AbstractController {
	/**
	 * Service that return the view
	 */
	private OlapService<Result> olapService;

	/**
	 * Setter injection
	 * @param olapService Service that return the view
	 */
	public void setOlapService(OlapService<Result> olapService) {
		this.olapService = olapService;
	}
	
	/**
	 * Execute a service that returns the dash board definition and map with a page that render in corresponding format (json)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Get menu definition
		List<MenuItemDefinition> menuDefinition = olapService.getMenuDefinition();
		
		// Map to view
		return new ModelAndView("/jsp/menuDefinition2json.jsp", "menuDefinition", menuDefinition);
	}

}
