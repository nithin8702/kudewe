package kudewe.reports.services;

import java.util.List;
import java.util.Map;

import kudewe.reports.metadata.DashBoardDefinition;
import kudewe.reports.metadata.MenuItemDefinition;
import kudewe.reports.model.Filter;
import kudewe.reports.model.View;


/**
 * Service for perform olap operations
 * @author fer
 *
 */
public interface OlapService<Result> {
	/**
	 * Return a filter
	 * @param filterUrl Filter's url
	 * @param filters Filters to apply
	 * @return Filter
	 */
	Filter getFilter(String filterUrl, Map<String, Filter> filters);
	
	/**
	 * Return a view
	 * @param viewUrl View's url
	 * @param filters Filters of view
	 * @return View
	 */
	View<Result> getView(String viewUrl, Map<String, Filter> filters);
	
	/**
	 * Returns a dash board definition by a given url 
	 * @param dashBoardUrl
	 * @return
	 */
	DashBoardDefinition getDashBoardDefinition(String dashBoardUrl);
	
	/**
	 * Returns user's menu definition  
	 * @return
	 */
	List<MenuItemDefinition> getMenuDefinition();
}
