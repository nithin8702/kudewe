package kudewe.reports.test.repository;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import kudewe.reports.repository.UrlMapper;
import kudewe.reports.test.common.BaseTest;

import org.junit.Test;


public class UrlMapperTest extends BaseTest {
	@Resource
	private UrlMapper urlMapperTest;
	
	@Test
	public void GetDashBoardDefinitionUrl() {
		assertEquals("sales/product", urlMapperTest.GetDashBoardDefinitionUrl("sales/product"));
		assertEquals("sales/product", urlMapperTest.GetDashBoardDefinitionUrl("sales/product/filter/brand"));
		assertEquals("sales/product", urlMapperTest.GetDashBoardDefinitionUrl("sales/product/view/productByMonth"));
	}
	
	@Test
	public void GetDashBoardDefinitionPath() {
		assertEquals("root/test/metadata/sales/product.xml", urlMapperTest.GetDashBoardDefinitionPath("sales/product"));
		assertEquals("root/test/metadata/sales/product.xml", urlMapperTest.GetDashBoardDefinitionPath("sales/product/filter/brand"));
		assertEquals("root/test/metadata/sales/product.xml", urlMapperTest.GetDashBoardDefinitionPath("sales/product/view/productByMonth"));
	}
	
	@Test
	public void GetFilterName() {
		assertEquals("brand", urlMapperTest.GetFilterName("sales/product/filter/brand"));
	}
	
	@Test
	public void GetViewName() {
		assertEquals("productByMonth", urlMapperTest.GetViewName("sales/product/view/productByMonth"));
	}
	
	@Test
	public void GetMenuDefinitionPath() {
		assertEquals("root/test/metadata/menu.xml", urlMapperTest.GetMenuDefinitionPath("menu"));
	}
}
