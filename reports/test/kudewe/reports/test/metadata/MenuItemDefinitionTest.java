package kudewe.reports.test.metadata;

import java.util.List;

import junit.framework.Assert;
import kudewe.reports.metadata.MenuItemDefinition;
import kudewe.reports.test.common.BaseTest;

import org.junit.Test;



public class MenuItemDefinitionTest extends BaseTest {
	
	/**
	 * Add item and test that getItems method return a copy
	 */
	@Test
	public void testAddItem() {
		MenuItemDefinition parentItem = new MenuItemDefinition();

		MenuItemDefinition childItem1 = new MenuItemDefinition();
		childItem1.setName("childItem1");
		parentItem.addItem(childItem1);
		
		List<MenuItemDefinition> childs = parentItem.getItems();
		
		MenuItemDefinition childItem2 = new MenuItemDefinition();
		childItem2.setName("childItem2");
		childs.add(childItem2);
		
		Assert.assertEquals(1, parentItem.getItems().size());
	}
}
