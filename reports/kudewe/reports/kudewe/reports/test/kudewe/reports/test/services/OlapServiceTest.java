package kudewe.reports.test.services;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kudewe.reports.metadata.DashBoardDefinition;
import kudewe.reports.metadata.MenuItemDefinition;
import kudewe.reports.model.Filter;
import kudewe.reports.model.View;
import kudewe.reports.services.OlapService;
import kudewe.reports.test.common.BaseTest;

import mondrian.olap.Result;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class OlapServiceTest extends BaseTest {
	@Autowired
	private OlapService<Result> olapService;
	
	@Test
	public void getFilter() {
		Map<String, Filter> filters = new HashMap<String, Filter>();
		Filter filter = olapService.getFilter("sales/product/filter/brand", filters);
		assertNotNull(filter);
		assertEquals("brand", filter.getName());
		assertTrue(filter.getItems().size() > 0);
	}
	
	@Test
	public void getView() {
		Map<String, Filter> filters = new HashMap<String, Filter>();
		
		View<Result> view = olapService.getView("sales/product/view/productsByMonth", filters);
		assertNotNull(view);
	}
	
	@Test
	public void getDashBoard() {
		DashBoardDefinition dashBoard = olapService.getDashBoardDefinition("sales/product");
		assertNotNull(dashBoard);
	}
	
	@Test
	public void getMenu() {
		List<MenuItemDefinition> menu = olapService.getMenuDefinition();
		assertNotNull(menu);
		assertTrue(menu.size() > 0);
	}

}
