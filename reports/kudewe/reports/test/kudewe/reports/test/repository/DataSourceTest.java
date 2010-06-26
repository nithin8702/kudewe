package kudewe.reports.test.repository;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import kudewe.reports.repository.DataSource;
import kudewe.reports.test.common.BaseTest;

import mondrian.olap.Result;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class DataSourceTest extends BaseTest {
	@Autowired
	private DataSource<Result> dataSource;
	
	@Resource(name="filterQuery")
	private String filterQuery;
	
	@Resource(name="connectionString")
	private String connectionString;
	
	@Test
	public void executeQuery() {
		Result result = dataSource.ExecuteQuery(connectionString, filterQuery);
		assertNotNull(result);
	}
}
