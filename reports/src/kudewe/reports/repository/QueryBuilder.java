package kudewe.reports.repository;

import java.util.Map;

import kudewe.reports.model.Filter;


/**
 * Build a query binding filter values
 * @author fer
 *
 */
public interface QueryBuilder {
	/**
	 * Build a query binding a query string with filters  
	 * @param query Query string
	 * @param filters Collection of filters
	 * @return Query with filter values
	 */
	String buildQuery(String query, Map<String, Filter> filters);
}
