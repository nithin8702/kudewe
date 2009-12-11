package kudewe.reports.repository;

import java.util.Map;

import kudewe.reports.metadata.ViewDefinition;
import kudewe.reports.model.Filter;


/**
 * Repository of views
 * @author fer
 *
 * @param <Result> View format
 */
public interface ViewRepository<Result> {
	/**
	 * Return a view from repository
	 * @param viewDefinition View's definition
	 * @param filters Filters of view
	 * @return
	 */
	Result getView(ViewDefinition viewDefinition, Map<String, Filter> filters);
}
