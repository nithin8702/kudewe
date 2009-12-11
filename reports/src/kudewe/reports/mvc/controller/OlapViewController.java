package kudewe.reports.mvc.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kudewe.reports.metadata.GenericDefinition;
import kudewe.reports.model.Filter;
import kudewe.reports.model.View;
import kudewe.reports.mvc.view.OlapResultAdapter;
import kudewe.reports.services.OlapService;

import mondrian.olap.Result;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


/**
 * A controller for olap view
 * Execute a service that returns the view and map with a page that render in corresponding format (json)
 * @author fer
 *
 */
public class OlapViewController extends AbstractController {
	/**
	 * Service that return the view
	 */
	private OlapService<Result> olapService;
	
	/**
	 * Wapper for olap result. It is used by the view to perform iterations over axis data using jstl.
	 */
	private OlapResultAdapter olapResultAdapterSingleton;
	
	/**
	 * Setter injection
	 * @param olapService Service that return the view
	 */
	public void setOlapService(OlapService<Result> olapService) {
		this.olapService = olapService;
	}
	
	/**
	 * Setter injection
	 * @param olapResultAdapter Wapper for olap result. It is used by the view to perform iterations over axis data using jstl.
	 */
	public void setOlapResultAdapter(OlapResultAdapter olapResultAdapter) {
		this.olapResultAdapterSingleton = olapResultAdapter;
	}
	
	/**
	 * Execute a service that returns the view and map with a page that render in corresponding format (json)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String startUrlToken = "/olap/";
		final String endUrlToken = ".json";
		
		// Get view's url
		String requestedUrl = request.getRequestURI();
		String viewUrl = requestedUrl.substring(requestedUrl.indexOf(startUrlToken) + startUrlToken.length(), requestedUrl.indexOf(endUrlToken));
		
		// Build filter map from request
		Map<String, Filter> filters = new HashMap<String, Filter>();
		Enumeration<String> paramNames = (Enumeration<String>) request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String filterName = paramNames.nextElement();
			String filterValue = request.getParameter(filterName);
			Filter filter = new Filter(filterName, filterValue);
			filters.put(filterName, filter);
		}
		
		// Get view
		View<Result> view = olapService.getView(viewUrl, filters);
		
		// Clone olap result wrapper because concurrent access
		OlapResultAdapter olapResultWrapper = olapResultAdapterSingleton.clone();
		olapResultWrapper.setResult(view.getData());
		
		// Get lookType
		String lookType = null;
		GenericDefinition lookTypeDefinition = view.getDefinition().getLookDefinition().getProperty("lookType"); 
		if (lookTypeDefinition != null) {
			lookType = lookTypeDefinition.getValue().toString();
		}
		
		// Map to view
		ModelAndView modelAndView = new ModelAndView("/jsp/view2json.jsp");
		modelAndView.addObject("view", view);
		modelAndView.addObject("lookType", lookType);
		modelAndView.addObject("viewAdapter", olapResultWrapper);
		return modelAndView;
	}

}
