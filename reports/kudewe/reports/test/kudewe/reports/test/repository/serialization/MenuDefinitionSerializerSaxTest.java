package kudewe.reports.test.repository.serialization;

import static org.junit.Assert.assertEquals;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import javax.annotation.Resource;

import kudewe.reports.metadata.MenuItemDefinition;
import kudewe.reports.repository.serialization.Serializer;
import kudewe.reports.test.common.BaseTest;

import org.junit.Test;
import org.xml.sax.InputSource;



public class MenuDefinitionSerializerSaxTest extends BaseTest {
	@Resource(name="menuDefinitionSerializerSax")
	Serializer<List<MenuItemDefinition>, InputSource> serializer;
	
	@Test
	public void testDeSerealize() {
		Reader reader = new StringReader(
			"<items>" +
				"<menu>" +
					"<name>Ventas</name>" +
					"<items>" +
						"<menu>" +
							"<name>Por Producto</name>" +
							"<dashBoardUrl>sales/product</dashBoardUrl>" +
						"</menu>" +
						"<menu>" +
							"<name>Por Marca</name>" +
							"<dashBoardUrl>sales/brand</dashBoardUrl>" +
						"</menu>" +
					"</items>" +
				"</menu>" +
				"<menu>" +
					"<name>Stock</name>" +
					"<items>" +
						"<menu>" +
							"<name>Por Marca</name>" +
							"<dashBoardUrl>stock/brand</dashBoardUrl>" +
						"</menu>" +
					"</items>" +
				"</menu>" +
			"</items>");
		
		InputSource source = new InputSource(reader);
		
		List<MenuItemDefinition> menuDefinition = serializer.DeSerealize(source);
		
		assertEquals("Ventas", menuDefinition.get(0).getName());
		assertEquals(2, menuDefinition.get(0).getItems().size());
		
		assertEquals("Por Producto", menuDefinition.get(0).getItems().get(0).getName());
		assertEquals("sales/product", menuDefinition.get(0).getItems().get(0).getDashBoardUrl());
		
		assertEquals("Por Marca", menuDefinition.get(0).getItems().get(1).getName());
		assertEquals("sales/brand", menuDefinition.get(0).getItems().get(1).getDashBoardUrl());
		
		assertEquals("Stock", menuDefinition.get(1).getName());
		assertEquals(1, menuDefinition.get(1).getItems().size());
		
		assertEquals("Por Marca", menuDefinition.get(1).getItems().get(0).getName());
		assertEquals("stock/brand", menuDefinition.get(1).getItems().get(0).getDashBoardUrl());
	}

}
