package kudewe.reports.test.metadata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import kudewe.reports.metadata.ConnectionDefinition;
import kudewe.reports.metadata.DashBoardDefinition;
import kudewe.reports.metadata.DataSourceDefinition;
import kudewe.reports.metadata.FilterDefinition;
import kudewe.reports.metadata.ViewDefinition;
import kudewe.reports.test.common.BaseTest;

import org.junit.Before;
import org.junit.Test;



public class DashBoardDefinitionTest extends BaseTest {
	
	private DashBoardDefinition dashBoard;
	private ViewDefinition view1;
	private ViewDefinition view2;
	private ViewDefinition view3;
	private FilterDefinition filter1;
	private FilterDefinition filter2;
	private FilterDefinition filter3;
	
	@Before
	public void setUp() throws Exception {
		dashBoard = new DashBoardDefinition();
		dashBoard.setConnection(new ConnectionDefinition());
		dashBoard.getConnection().setCubeAlias("connectionA");
		
		// Test view
		// Set 1st data source, then dash board
		view1 = new ViewDefinition();
		view1.setName("view1");
		view1.setDataSourceDefinition(new DataSourceDefinition());
		dashBoard.addView(view1);
		
		// Test view
		// Set 1st dash board, then data source
		view2 = new ViewDefinition();
		view2.setName("view2");
		dashBoard.addView(view2);
		view2.setDataSourceDefinition(new DataSourceDefinition());
		
		// Test view
		// with connection
		view3 = new ViewDefinition();
		view3.setName("view3");
		view3.setDataSourceDefinition(new DataSourceDefinition());
		view3.getDataSourceDefinition().setConnection(new ConnectionDefinition());
		view3.getDataSourceDefinition().getConnection().setCubeAlias("connectionB");
		dashBoard.addView(view3);
		
		// Test filter
		// Set 1st data source, then dash board
		filter1 = new FilterDefinition();
		filter1.setName("filter1");
		filter1.setDataSourceDefinition(new DataSourceDefinition());
		dashBoard.addFilter(filter1);
		
		// Test filter
		// Set 1st dash board, then data source
		filter2 = new FilterDefinition();
		filter2.setName("filter2");
		dashBoard.addFilter(filter2);
		filter2.setDataSourceDefinition(new DataSourceDefinition());
		
		// Test filter
		// with connection
		filter3 = new FilterDefinition();
		filter3.setName("filter3");
		filter3.setDataSourceDefinition(new DataSourceDefinition());
		filter3.getDataSourceDefinition().setConnection(new ConnectionDefinition());
		filter3.getDataSourceDefinition().getConnection().setCubeAlias("connectionB");
		dashBoard.addFilter(filter3);
		
	}
	
	@Test
	public void testConnection() {
		
		// Test view
		// Set 1st data source, then dash board
		assertEquals("connectionA", view1.getDataSourceDefinition().getConnection().getCubeAlias());
		assertNotNull(view1.getDashBoardDefinition());
		
		// Test view
		// Set 1st dash board, then data source
		assertEquals("connectionA", view2.getDataSourceDefinition().getConnection().getCubeAlias());
		assertNotNull(view2.getDashBoardDefinition());
		
		// Test view
		// with connection
		assertEquals("connectionB", view3.getDataSourceDefinition().getConnection().getCubeAlias());
		assertNotNull(view3.getDashBoardDefinition());
		
		// Test filter
		// Set 1st data source, then dash board
		assertEquals("connectionA", filter1.getDataSourceDefinition().getConnection().getCubeAlias());
		assertNotNull(filter1.getDashBoardDefinition());
		
		// Test filter
		// Set 1st dash board, then data source
		assertEquals("connectionA", filter2.getDataSourceDefinition().getConnection().getCubeAlias());
		assertNotNull(filter2.getDashBoardDefinition());
		
		// Test filter
		// with connection
		assertEquals("connectionB", filter3.getDataSourceDefinition().getConnection().getCubeAlias());
		assertNotNull(filter3.getDashBoardDefinition());
		
	}
	
	@Test
	public void testGetFilter() {
		FilterDefinition filter;
		
		filter = dashBoard.getFilter("filter1");
		assertEquals("filter1", filter.getName());
		filter = dashBoard.getFilter("filter2");
		assertEquals("filter2", filter.getName());
		filter = dashBoard.getFilter("filter3");
		assertEquals("filter3", filter.getName());
	}
	
	@Test
	public void testGetView() {
		ViewDefinition view;
		
		view = dashBoard.getView("view1");
		assertEquals("view1", view.getName());
		view = dashBoard.getView("view2");
		assertEquals("view2", view.getName());
		view = dashBoard.getView("view3");
		assertEquals("view3", view.getName());
	}	
}
