package kudewe.reports.repository;

import java.util.List;

import kudewe.reports.metadata.DashBoardDefinition;
import kudewe.reports.metadata.FilterDefinition;
import kudewe.reports.metadata.MenuItemDefinition;
import kudewe.reports.metadata.ViewDefinition;


public interface MetadataRepository {
	/**
	 * Returns view definition of given url
	 * @param url view definition's url
	 * @return view definition
	 */
	ViewDefinition getViewDefinition(String url);
	
	/**
	 * Returns filter definition of given url
	 * @param url filter definition's url
	 * @return filter definition
	 */
	FilterDefinition getFilterDefinition(String url);
	
	/**
	 * Returns dash board definition of given url
	 * @param url dash board definition's url
	 * @return dash board definition
	 */
	DashBoardDefinition getDashBoardDefinition(String url);
	
	/**
	 * Returns user's menu definition
	 * @param url menu definition's url
	 * @return menu definition
	 */
	List<MenuItemDefinition> getMenuDefinition(String url);
	
}
