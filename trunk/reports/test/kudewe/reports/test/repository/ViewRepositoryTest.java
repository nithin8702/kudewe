package kudewe.reports.test.repository;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import kudewe.reports.metadata.ConnectionDefinition;
import kudewe.reports.metadata.DataSourceDefinition;
import kudewe.reports.metadata.ViewDefinition;
import kudewe.reports.model.Filter;
import kudewe.reports.repository.ViewRepository;
import kudewe.reports.test.common.BaseTest;

import mondrian.olap.Result;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class ViewRepositoryTest extends BaseTest {
	@Autowired
	private ViewRepository<Result> viewRepository;
	
	@Test
	public void getView() {
		ViewDefinition viewDefinition = new ViewDefinition();
		viewDefinition.setName("productsByMonth");
		viewDefinition.setDataSourceDefinition(new DataSourceDefinition());
		viewDefinition.getDataSourceDefinition().setConnection(new ConnectionDefinition());
		viewDefinition.getDataSourceDefinition().getConnection().setCubeAlias("cube_sales");
		viewDefinition.getDataSourceDefinition().getConnection().setDataBaseAlias("testdb");
		viewDefinition.getDataSourceDefinition().getConnection().setTemplate("hsqldb");
		viewDefinition.getDataSourceDefinition().setQuery(
	    	"SELECT {[Measures].[Quantity], [Measures].[SalePrice]} ON COLUMNS, " +
	    	"{[Product].members} on rows " +
	    	"FROM [Sales] " +
	    	"WHERE ${brand}"
		);

		Map<String, Filter> filters = new HashMap<String, Filter>();
		Result result = viewRepository.getView(viewDefinition, filters);
		assertNotNull(result);
	}
	
}
