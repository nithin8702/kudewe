package kudewe.reports.test.mvc.view;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import kudewe.reports.metadata.GenericDefinition;
import kudewe.reports.mvc.view.GenericDefinitionAdapter;
import kudewe.reports.test.common.BaseTest;

import org.junit.Before;
import org.junit.Test;


public class GenericDefinitionAdapterTest extends BaseTest {
	private GenericDefinitionAdapter lookAdapter;
	
	@Before
	public void setUp() {
		GenericDefinition look = new GenericDefinition();
		
		// Simple properties
		look.addProperty(new GenericDefinition("title", "Productos por Mes"));
		look.addProperty(new GenericDefinition("width", 400));
		look.addProperty(new GenericDefinition("height", 200));
		
		// Array string property
		List<String> fields = new ArrayList<String>();
		fields.add("Product");
		fields.add("SalePrice");
		fields.add("Quantity");
		
		look.addProperty(new GenericDefinition("fields", fields));
		
		// Array definition property
		List<GenericDefinition> columns = new ArrayList<GenericDefinition>();
		
		GenericDefinition column1 = new GenericDefinition();
		column1.addProperty(new GenericDefinition("header", "Product"));
		column1.addProperty(new GenericDefinition("dataIndex", "Product"));
		columns.add(column1);
		
		GenericDefinition column2 = new GenericDefinition();
		column2.addProperty(new GenericDefinition("header", "Precio Venta $"));
		column2.addProperty(new GenericDefinition("dataIndex", "SalePrice"));
		columns.add(column2);
		
		GenericDefinition column3 = new GenericDefinition();
		column3.addProperty(new GenericDefinition("header", "Cantidad"));
		column3.addProperty(new GenericDefinition("dataIndex", "Quantity"));
		columns.add(column3);
		
		look.addProperty(new GenericDefinition("columns", columns));
		
		lookAdapter = new GenericDefinitionAdapter(look);
	}
	
	@Test
	public void getSimpleProperties() {
		List<GenericDefinition> properties = lookAdapter.getSimpleProperties();
		Assert.assertEquals(3, properties.size());
		Assert.assertEquals("width", properties.get(1).getName());
		Assert.assertEquals(200, properties.get(2).getValue());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getSimpleArrayProperties() {
		List<GenericDefinition> properties = lookAdapter.getSimpleArrayProperties();
		Assert.assertEquals(1, properties.size());
		
		GenericDefinition fields = properties.get(0);
		Assert.assertEquals("fields", fields.getName());
		List<String> fieldsList = (List<String>) fields.getValue();
		Assert.assertEquals(3, fieldsList.size());
		Assert.assertEquals("SalePrice", fieldsList.get(1));
		Assert.assertEquals("Quantity", fieldsList.get(2));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getComplexArrayProperties() {
		List<GenericDefinitionAdapter> properties = lookAdapter.getComplexArrayProperties();
		Assert.assertEquals(1, properties.size());
		
		GenericDefinitionAdapter columns = properties.get(0);
		Assert.assertEquals("columns", columns.getName());
		List<GenericDefinitionAdapter> columnsList = (List<GenericDefinitionAdapter>) columns.getValue();
		Assert.assertEquals(3, columnsList.size());
		
		GenericDefinitionAdapter column1 = columnsList.get(0);
		Assert.assertEquals("header", column1.getSimpleProperties().get(0).getName());
		Assert.assertEquals("Product", column1.getSimpleProperties().get(0).getValue());
		
		GenericDefinitionAdapter column2 = columnsList.get(1);
		Assert.assertEquals("dataIndex", column2.getSimpleProperties().get(1).getName());
		Assert.assertEquals("SalePrice", column2.getSimpleProperties().get(1).getValue());

	}
}
