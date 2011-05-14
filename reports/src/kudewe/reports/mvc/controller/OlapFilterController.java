package kudewe.reports.mvc.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kudewe.reports.model.Filter;
import kudewe.reports.services.OlapService;

import mondrian.olap.Result;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


/**
 * A controller for olap filter
 * Execute a service that returns the filter and map with a page that render in corresponding format (json)
 * @author fer
 *
 */
public class OlapFilterController extends AbstractController {
	/**
	 * Service that return the filter
	 */
	private OlapService<Result> olapService;
	
	/**
	 * Setter injection
	 * @param olapService Service that return the filter
	 */
	public void setOlapService(OlapService<Result> olapService) {
		this.olapService = olapService;
	}
	
	/**
	 * Execute a service that returns the filter and map with a page that render in corresponding format (json)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		final String startUrlToken = "/olap/";
		final String endUrlToken = ".json";
		
		// Get filter's url
		String requestedUrl = request.getRequestURI();
		String filterUrl = requestedUrl.substring(requestedUrl.indexOf(startUrlToken) + startUrlToken.length(), requestedUrl.indexOf(endUrlToken));
		
		// Build filter map from request
		Map<String, Filter> filters = new HashMap<String, Filter>();
		String filtersParam = request.getParameter("filter");
		if (filtersParam != null) {
			String[] filtersArray = filtersParam.split("&");
			for (String filterParam : filtersArray) {
				String[] filterArray = filterParam.split("="); 
				String filterName = filterArray[0];
				String filterValue = filterArray[1];
				Filter filter = new Filter(filterName, filterValue);
				filters.put(filterName, filter);
			}
		}
		
		//Enumeration<String> paramNames = (Enumeration<String>) request.getParameterNames();
		//while (paramNames.hasMoreElements()) {
			//String filterName = paramNames.nextElement();
			//String filterValue = request.getParameter(filterName);
			//Filter filter = new Filter(filterName, filterValue);
			//filters.put(filterName, filter);
		//}
		
		// Get filter
		Filter filter = olapService.getFilter(filterUrl, filters);
		
		// Map to view
		return new ModelAndView("/jsp/filter2json.jsp", "filter", filter);
	}
}
