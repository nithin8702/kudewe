package kudewe.reports.metadata;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a data source for views and filters
 * @author fer
 *
 */
public class DataSourceDefinition {
	/**
	 * The data source's query
	 */
	private String query;

	/**
	 * The data source's connection
	 */
	private ConnectionDefinition connection;
	
	/**
	 * The view's dash board
	 */
	private DashBoardDefinition dashBoard;
	
	/**
	 * Filter's dependencies
	 */
	private List<String> dependencies = new ArrayList<String>();
	
	/**
	 * Return data source's query
	 * @return
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Set data source's query
	 * @param query
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Return data source's connection
	 * @return
	 */
	public ConnectionDefinition getConnection() {
		ConnectionDefinition connection = this.connection;
		if (connection == null && this.dashBoard != null) {
			connection = dashBoard.getConnection();
		}
		return connection;
	}

	/**
	 * Set data source's connection
	 * @param connection
	 */
	public void setConnection(ConnectionDefinition connection) {
		this.connection = connection;
	}

	/**
	 * Set data source's dash board
	 */
	public void setDashBoard(DashBoardDefinition dashBoard) {
		this.dashBoard = dashBoard;
	}
	
	/**
	 * Get filter's dependencies
	 */
	public List<String> getDependencies() {
		return dependencies;
	}

}
