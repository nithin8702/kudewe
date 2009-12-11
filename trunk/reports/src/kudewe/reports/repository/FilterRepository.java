package kudewe.reports.repository;

import java.util.Map;

import kudewe.reports.metadata.FilterDefinition;
import kudewe.reports.model.Filter;


/**
 * Repository of filters
 * @author fer
 *
 */
public interface FilterRepository {
	/**
	 * Return a filter from repository
	 * @param definition filter's definition
	 * @param filters Filters to apply
	 * @return Filter
	 */
	Filter getFilter(FilterDefinition filterDefinition, Map<String, Filter> filters);
}
