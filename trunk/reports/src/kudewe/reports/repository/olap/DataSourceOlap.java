package kudewe.reports.repository.olap;

import kudewe.reports.repository.DataSource;
import mondrian.olap.Connection;
import mondrian.olap.DriverManager;
import mondrian.olap.Query;
import mondrian.olap.Result;

/**
 * Encapsulate access to a data source
 * @author fer
 *
 */
public class DataSourceOlap implements DataSource<Result> {
	
	/**
	 * Execute a query and return data from data source
	 * @param connection Connection to data soure
	 * @param query Query to be executed
	 * @return Result of execution
	 */
	@Override
	public Result ExecuteQuery(String connection, String query) {
		// Load the HSQL Database Engine JDBC driver
        // hsqldb.jar should be in the class path or made part of the current jar
        try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	    // Retrieve a connection from the DriveManager
	    Connection olapConnection = DriverManager.getConnection(connection, null);
	    
	    // Generate a MDX Query object
	    Query olapQuery = olapConnection.parseQuery(query);

	    // Execute the query
	    return olapConnection.execute(olapQuery);
	    
	}
	
}
