package kudewe.reports.test.repository;

import static org.junit.Assert.*;

import java.util.List;

import kudewe.reports.metadata.DashBoardDefinition;
import kudewe.reports.metadata.FilterDefinition;
import kudewe.reports.metadata.MenuItemDefinition;
import kudewe.reports.metadata.ViewDefinition;
import kudewe.reports.repository.MetadataRepository;
import kudewe.reports.test.common.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class MetadataRepositoryTest extends BaseTest {
	@Autowired
	private MetadataRepository metadataRepository;
	
	@Test
	public void testGetFilterDefinition() {
		FilterDefinition filterDefinition = metadataRepository.getFilterDefinition("sales/product/filter/brand");
		assertEquals("brand", filterDefinition.getName());
		assertEquals("sales/product/filter/brand", filterDefinition.getUrl());
	}

	@Test
	public void testGetViewDefinition() {
		ViewDefinition viewDefinition = metadataRepository.getViewDefinition("sales/product/view/productsByMonth");
		assertEquals("productsByMonth", viewDefinition.getName());
		assertEquals("sales/product/view/productsByMonth", viewDefinition.getUrl());
	}

	@Test
	public void testGetDashBoardDefinition() {
		DashBoardDefinition dashBoardDefinition = metadataRepository.getDashBoardDefinition("sales/product");
		assertNotNull(dashBoardDefinition);
		assertEquals("sales/product", dashBoardDefinition.getUrl());
	}

	@Test
	public void testMenuDefinition() {
		List<MenuItemDefinition> menuDefinition = metadataRepository.getMenuDefinition("menu");
		assertNotNull(menuDefinition);
		assertTrue(menuDefinition.size() > 0);
		assertEquals("Ventas", menuDefinition.get(0).getName());
		assertEquals(2, menuDefinition.get(0).getItems().size());
	}
}
