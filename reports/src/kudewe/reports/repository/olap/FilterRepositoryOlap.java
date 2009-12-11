package kudewe.reports.repository.olap;

import java.util.Map;

import kudewe.reports.metadata.DataSourceDefinition;
import kudewe.reports.metadata.FilterDefinition;
import kudewe.reports.model.Filter;
import kudewe.reports.model.FilterItem;
import kudewe.reports.repository.ConnectionStringBuilder;
import kudewe.reports.repository.DataSource;
import kudewe.reports.repository.FilterRepository;
import kudewe.reports.repository.QueryBuilder;

import mondrian.olap.Position;
import mondrian.olap.Result;


/**
 * Repository of filters
 * @author fer
 *
 */
public class FilterRepositoryOlap implements FilterRepository {
	/**
	 * Data source object
	 */
	private DataSource<Result> dataSource;
	
	/**
	 * Strategy for build connection string
	 */
	private ConnectionStringBuilder connectionStringBuilder;
	
	/**
	 * Query builder
	 */
	private QueryBuilder queryBuilder;
	
	/**
	 * Setter injection
	 * @param dataSource Data source object
	 */
	public void setDataSource(DataSource<Result> dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * Setter injection
	 * @param dataSource Data source object
	 */
	public void setConnectionStringBuilder(ConnectionStringBuilder connectionStringBuilder) {
		this.connectionStringBuilder = connectionStringBuilder;
	}

	/**
	 * Setter injection
	 * @param dataSource Data source object
	 */
	public void setQueryBuilder(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}
	
	/**
	 * Return a filter from repository
	 * @param definition filter's definition
	 * @param filters Filters to apply
	 * @return Filter
	 */
	@Override
	public Filter getFilter(FilterDefinition filterDefinition, Map<String, Filter> filters) {
		Filter filter = new Filter(filterDefinition.getName());
		populateItems(filter, filterDefinition.getDataSourceDefinition(), filters);
		return filter;
	}
	
	/**
	 * Execute query and populate filter with the result 
	 * @param filter Filter to populate
	 * @param dataSourceDefinition Data source definition
	 * @param filters Filters to apply
	 */
	private void populateItems(Filter filter, DataSourceDefinition dataSourceDefinition, Map<String, Filter> filters) {
		// Build query binded
	    String queryBinded = queryBuilder.buildQuery(dataSourceDefinition.getQuery(), filters);
	    
	    // Execute query and obtain result
	    Result result = dataSource.ExecuteQuery(connectionStringBuilder.buildConnectionString(dataSourceDefinition.getConnection()), 
	    		queryBinded);
	    
	    for(Position position : result.getAxes()[1].getPositions()){
	    	FilterItem filterItem = new FilterItem(position.get(0).getUniqueName(), position.get(0).getCaption());
	    	filter.getItems().add(filterItem);
	    }
	}

}
