package kudewe.reports.repository.olap;

import java.util.Map;

import kudewe.reports.metadata.ViewDefinition;
import kudewe.reports.model.Filter;
import kudewe.reports.repository.ConnectionStringBuilder;
import kudewe.reports.repository.DataSource;
import kudewe.reports.repository.QueryBuilder;
import kudewe.reports.repository.ViewRepository;

import mondrian.olap.Result;

/**
 * Repository of views
 * @author fer
 *
 */
public class ViewRepositoryOlap implements ViewRepository<Result> {
	/**
	 * Data source object
	 */
	private DataSource<Result> dataSource;
	
	/**
	 * Query builder
	 */
	private QueryBuilder queryBuilder;
	
	/**
	 * Strategy for build connection string
	 */
	private ConnectionStringBuilder connectionStringBuilder;
	
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
	public void setQueryBuilder(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}
	
	/**
	 * Setter injection
	 * @param dataSource Data source object
	 */
	public void setConnectionStringBuilder(ConnectionStringBuilder connectionStringBuilder) {
		this.connectionStringBuilder = connectionStringBuilder;
	}
	
	/**
	 * Return a view from repository
	 * @param viewDefinition View's definition
	 * @param filters Filters of view
	 * @return
	 */
	@Override
	public Result getView(ViewDefinition viewDefinition, Map<String, Filter> filters) {
		// Build query binded
	    String queryBinded = queryBuilder.buildQuery(viewDefinition.getDataSourceDefinition().getQuery(), filters);
	    
	    // Execute query and obtain result
	    return dataSource.ExecuteQuery(connectionStringBuilder.buildConnectionString(viewDefinition.getDataSourceDefinition().getConnection()),
	    		queryBinded);
	}

}
