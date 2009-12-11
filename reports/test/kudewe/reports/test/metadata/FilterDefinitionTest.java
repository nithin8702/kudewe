package kudewe.reports.test.metadata;

import junit.framework.Assert;
import kudewe.reports.metadata.DashBoardDefinition;
import kudewe.reports.metadata.FilterDefinition;
import kudewe.reports.test.common.BaseTest;

import org.junit.Test;


public class FilterDefinitionTest extends BaseTest {
	@Test
	public void getUrl() {
		DashBoardDefinition dashBoard = new DashBoardDefinition();
		dashBoard.setUrl("sales/product");
		FilterDefinition filter = new FilterDefinition();
		filter.setName("brand");
		dashBoard.addFilter(filter);
		
		Assert.assertEquals("sales/product/filter/brand", filter.getUrl());
	}
}
