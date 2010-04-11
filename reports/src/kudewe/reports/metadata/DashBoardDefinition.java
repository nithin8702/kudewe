package kudewe.reports.metadata;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Defines a dashboard
 * @author fer
 *
 */
public class DashBoardDefinition {
	/**
	 * The dashboard's url. Identify view as url address 
	 */
	private String url;
	
	/**
	 * The dashboard's connection.
	 */
	private ConnectionDefinition connection;

	/**
	 * The dashboard's views
	 */
	private Map<String, ViewDefinition> views = new LinkedHashMap<String, ViewDefinition>();

	/**
	 * The dashboard's filters
	 */
	//TODO to use sorted data structure
	private Map<String, FilterDefinition> filters = new LinkedHashMap<String, FilterDefinition>();
	
	/**
	 * The dashboard's look
	 */
	private GenericDefinition lookDefinition;
	
	/**
	 * Return dashboard's url. Identify view as url address
	 * @return
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Set dashboard's url. Identify view as url address
	 * @param viewUrl
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Return dashboard's connection.
	 * @return
	 */
	public ConnectionDefinition getConnection() {
		return connection;
	}

	/**
	 * Set dashboard's connection.
	 * @param dataSourceDefinition
	 */
	public void setConnection(ConnectionDefinition connection) {
		this.connection = connection;
	}

	/**
	 * Return dashboard's views
	 * @return
	 */
	public List<ViewDefinition> getViews() {
		return new ArrayList<ViewDefinition>(views.values());
	}

	/**
	 * Return dashboard's filters
	 * @return
	 */
	public List<FilterDefinition> getFilters() {
		return new ArrayList<FilterDefinition>(filters.values()); 
	}
	
	/**
	 * Add a view to dash board
	 * @param view
	 */
	public void addView(ViewDefinition view) {
		views.put(view.getName(), view);
		view.setDashBoardDefinition(this);
	}
	
	/**
	 * Add a filter to dash board
	 * @param filter
	 */
	public void addFilter(FilterDefinition filter) {
		filters.put(filter.getName(), filter);
		filter.setDashBoardDefinition(this);
	}
	
	/**
	 * Return a filter definition by given name
	 * @param name
	 * @return
	 */
	public FilterDefinition getFilter(String name) {
		return filters.get(name);
	}
	
	/**
	 * Return a view definition by given name
	 * @param name
	 * @return
	 */
	public ViewDefinition getView(String name) {
		return views.get(name);
	}
	
	/**
	 * Return view's look
	 */
	public GenericDefinition getLookDefinition() {
		return lookDefinition;
	}

	/**
	 * Set view's look
	 */
	public void setLookDefinition(GenericDefinition lookDefinition) {
		this.lookDefinition = lookDefinition;
	}
}
