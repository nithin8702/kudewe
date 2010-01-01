package kudewe.reports.test.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import kudewe.reports.metadata.ConnectionDefinition;
import kudewe.reports.metadata.DataSourceDefinition;
import kudewe.reports.metadata.FilterDefinition;
import kudewe.reports.model.Filter;
import kudewe.reports.repository.FilterRepository;
import kudewe.reports.test.common.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class FilterRepositorTest extends BaseTest {
	@Autowired
	private FilterRepository filterRepository;
	
	@Test
	public void getFilter() {
		FilterDefinition filterDefinition = new FilterDefinition();
		filterDefinition.setName("brand");
		filterDefinition.setDataSourceDefinition(new DataSourceDefinition());
		filterDefinition.getDataSourceDefinition().setConnection(new ConnectionDefinition());
		filterDefinition.getDataSourceDefinition().getConnection().setCubeAlias("cube_sales");
		filterDefinition.getDataSourceDefinition().getConnection().setDataBaseAlias("testdb");
		filterDefinition.getDataSourceDefinition().getConnection().setTemplate("hsqldb");
		filterDefinition.getDataSourceDefinition().setQuery(
	    	"WITH MEMBER [Measures].[one] AS '1' " +
	    	"SELECT {[Measures].[one]} ON COLUMNS, " +
	    	"{[Brand].members} on rows " +
	    	"FROM [Sales]"
		);
		
		Map<String, Filter> filters = new HashMap<String, Filter>();
		Filter filter = filterRepository.getFilter(filterDefinition, filters);
		assertEquals("brand", filter.getName());
		assertTrue("The filtes doesn't contain items", filter.getItems().size() > 0);
		assertEquals("All", filter.getItems().get(0).getDescription());
		assertEquals("[Brand].[All]", filter.getItems().get(0).getValue());
	}
}
