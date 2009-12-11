package kudewe.reports.repository;

/**
 * Encapsulate access to a data source
 * @author fer
 *
 * @param <Result> Format of data source
 */
public interface DataSource<Result> {
	/**
	 * Execute a query and return data from data source
	 * @param connection Connection to data soure
	 * @param query Query to be executed
	 * @return Result of execution
	 */
	Result ExecuteQuery(String connection, String query);
}
