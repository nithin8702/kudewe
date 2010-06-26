package kudewe.reports.test.metadata;

import junit.framework.Assert;
import kudewe.reports.metadata.DashBoardDefinition;
import kudewe.reports.metadata.ViewDefinition;
import kudewe.reports.test.common.BaseTest;

import org.junit.Test;


public class ViewDefinitionTest extends BaseTest {

	@Test
	public void getUrl() {
		DashBoardDefinition dashBoard = new DashBoardDefinition();
		dashBoard.setUrl("sales/product");
		ViewDefinition view = new ViewDefinition();
		view.setName("productByMonth");
		dashBoard.addView(view);
		
		Assert.assertEquals("sales/product/view/productByMonth", view.getUrl());
	}

}
