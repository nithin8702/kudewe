package kudewe.reports.test.model;

import junit.framework.Assert;
import kudewe.reports.model.Filter;
import kudewe.reports.test.common.BaseTest;

import org.junit.Test;


public class FilterTest extends BaseTest{
	@Test
	public void getSelectedWithoutDefault() {
		Filter filter = new Filter("brand");
		Assert.assertNull(filter.getSelectedValue());
		
		filter = new Filter("brand", "Aliverti");
		Assert.assertNotNull(filter.getSelectedValue());
		Assert.assertEquals("Aliverti", filter.getSelectedValue());
	}
	
	@Test
	public void getSelectedWithDefault() {
		Filter filter = new Filter("brand");
		Assert.assertEquals("Aliverti", filter.defaultValue("Aliverti").getSelectedValue());
		
		filter = new Filter("brand", "Aliverti");
		filter.defaultValue("Makalu");
		Assert.assertEquals("Aliverti", filter.getSelectedValue());
	}
	
	@Test
	public void getSelectedWithParent() {
		// Test year with selected value
		Filter year = new Filter("year", "[Time].[All].[2009]");
		Filter month = new Filter("month");
		month.parent(year);
		Assert.assertEquals("[Time].[All].[2009]", month.getSelectedValue());
		
		// Test year with default
		year = new Filter("year");
		month.parent(year).defaultValue("[Time].[All].[2009]");
		Assert.assertEquals("[Time].[All].[2009]", month.getSelectedValue());
	}
	
	@Test
	public void getSelectedWithDefaultAndParent() {
		Filter year = new Filter("year", "[Time].[All].[2009]");
		Filter month = new Filter("month");
		month.defaultValue("[Time].[All].[2009].[1].[Enero 2009]").parent(year);
		
		Assert.assertEquals("[Time].[All].[2009].[1].[Enero 2009]", month.getSelectedValue());
		
		// Change year
		year = new Filter("year", "[Time].[All].[2008]");
		month.parent(year);
		Assert.assertEquals("[Time].[All].[2008]", month.getSelectedValue());
		
		// Change month
		month = new Filter("month", "[Time].[All].[2008].[1].[Enero 2008]");
		month.parent(year);
		Assert.assertEquals("[Time].[All].[2008].[1].[Enero 2008]", month.getSelectedValue());
	}
}
