package kudewe.reports.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kudewe.reports.metadata.DashBoardDefinition;
import kudewe.reports.mvc.view.LookDefinitionIterator;
import kudewe.reports.services.OlapService;

import mondrian.olap.Result;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


/**
 * A controller for olap dash board definition
 * Execute a service that returns the dash board definition and map with a page that render in corresponding format (json)
 * @author fer
 *
 */
public class OlapDashBoardDefinitionController extends AbstractController {
	/**
	 * Service that return the view
	 */
	private OlapService<Result> olapService;

	/**
	 * Iterator over view's look definition
	 */
	private LookDefinitionIterator lookDefinitionIteratorSingleton;

	/**
	 * Setter injection
	 * @param olapService Service that return the view
	 */
	public void setOlapService(OlapService<Result> olapService) {
		this.olapService = olapService;
	}
	
	/**
	 * Setter injection
	 * @param lookDefinitionIterator Iterator over view's look definition
	 */
	public void setLookDefinitionIterator(LookDefinitionIterator lookDefinitionIterator) {
		this.lookDefinitionIteratorSingleton = lookDefinitionIterator;
	}
	

	/**
	 * Execute a service that returns the dash board definition and map with a page that render in corresponding format (json)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String startUrlToken = "/metadata/";
		final String endUrlToken = ".json";
		
		// Get dash board's url
		String requestedUrl = request.getRequestURI();
		String dashBoardDefinitionUrl = requestedUrl.substring(requestedUrl.indexOf(startUrlToken) + startUrlToken.length(), requestedUrl.indexOf(endUrlToken));
		
		// Get dash board definition
		DashBoardDefinition dashBoardDefinition = olapService.getDashBoardDefinition(dashBoardDefinitionUrl);
		
		// Clone olap look iterator because concurrent access
		LookDefinitionIterator lookDefinitionIterator = lookDefinitionIteratorSingleton.clone();
		lookDefinitionIterator.setViews(dashBoardDefinition.getViews());
		
		// Map to view
		ModelAndView modelAndView = new ModelAndView("/jsp/dashBoardDefinition2json.jsp", "dashBoardDefinition", dashBoardDefinition);
		modelAndView.addObject("lookDefinitionIterator", lookDefinitionIterator);
		return modelAndView;
	}
}
