package kudewe.reports.test.repository.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import javax.annotation.Resource;

import kudewe.reports.metadata.DashBoardDefinition;
import kudewe.reports.metadata.FilterDefinition;
import kudewe.reports.metadata.GenericDefinition;
import kudewe.reports.metadata.ViewDefinition;
import kudewe.reports.repository.serialization.Serializer;
import kudewe.reports.test.common.BaseTest;

import org.junit.Test;
import org.xml.sax.InputSource;



public class DashBoardDefinitionSerializerSaxTest extends BaseTest {
	@Resource(name="dashBoardDefinitionSerializerSax")
	Serializer<DashBoardDefinition, InputSource> serializer;
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeSerealize() {
		Reader reader = new StringReader(
			"<dashboard>" +
				"<connection>" +
					"<cubeAlias>cubeA</cubeAlias>" +
					"<dataBaseAlias>dbA</dataBaseAlias>" +
					"<template>templateA</template>" +
				"</connection>" +
				"<look>" +
					"<cols>2</cols>" +
				"</look>" +
				"<views>" +
					"<view>" +
						"<name>view1</name>" +
						"<datasource>" +
							"<query>" +
								"query view 1" +
							"</query>" +
							"<dependencies>" + 
								"<filter>month</filter>" +
								"<filter>brand</filter>" +
							"</dependencies>" +
						"</datasource>" +
						"<look>" +
							"<lookType>ext.grid</lookType>" +
							"<width>400</width>" +
							"<height>200</height>" +
							"<fields>Product</fields>" +
							"<fields>SalePrice</fields>" +
							"<fields>Quantity</fields>" +
							"<columns>" +
								"<header>Product</header>" +
								"<dataIndex>Product</dataIndex>" +
							"</columns>" +
							"<columns>" +
								"<header>Precio Venta $</header>" +
								"<dataIndex>SalePrice</dataIndex>" +
							"</columns>" +
							"<columns>" +
								"<header>Cantidad</header>" +
								"<dataIndex>Quantity</dataIndex>" +
							"</columns>" +
							"<series type=\"list\">" +
								"<type>line</type>" +
								"<displayName>Cantidad</displayName>" +
								"<yField>Quantity</yField>" +
								"<label>" +
									"<field>Product</field>" +
								"</label>" +
							"</series>" +
							"<axes>" +
								"<type>Numeric</type>" +
								"<position>left</position>" +
								"<fields>DirectCost</fields>" +
								"<fields>IndirectCost</fields>" +
								"<fields>TotalCost</fields>" +
							"</axes>" +
							"<axes>" +
								"<type>Category</type>" +
								"<position>bottom</position>" +
								"<fields type=\"list\">Time</fields>" +
							"</axes>" +
							"<legend>" +
								"<position>right</position>" +
							"</legend>" +
						"</look>" +
					"</view>" +
					"<view>" +
						"<name>view2</name>" +
						"<datasource>" +
							"<query>" +
								"query view 2" +
							"</query>" +
							"<connection>" +
								"<cubeAlias>cubeB</cubeAlias>" +
								"<dataBaseAlias>dbB</dataBaseAlias>" +
								"<template>templateB</template>" +
							"</connection>" +
						"</datasource>" +
						"<look type=\"ext.graph\">" +
							"<lookType>ext.graph</lookType>" +
						"</look>" +
					"</view>" +
				"</views>" +
				"<filters>" +
					"<filter>" +
						"<name>brand</name>" +
						"<label>Marca</label>" +
						"<datasource>" +
							"<query>" +
								"WITH MEMBER [Measures].[one] AS '1' " +
						    	"SELECT {[Measures].[one]} ON COLUMNS, " +
						    	"{[Brand].members} on rows " +
						    	"FROM [Sales]" +
							"</query>" +
							"<connection>" +
								"<cubeAlias>cubeC</cubeAlias>" +
								"<dataBaseAlias>dbC</dataBaseAlias>" +
								"<template>templateC</template>" +
							"</connection>" +
							"<dependencies>" + 
								"<filter>quarter</filter>" +
							"</dependencies>" +
						"</datasource>" +
					"</filter>" +
					"<filter>" +
						"<name>month</name>" +
						"<label>Mes</label>" +
						"<datasource>" +
							"<query>" +
								"WITH MEMBER [Measures].[one] AS '1' " +
						    	"SELECT {[Measures].[one]} ON COLUMNS, " +
						    	"{[Time].members} on rows " +
						    	"FROM [Sales]" +
							"</query>" +
							"<dependencies>" + 
								"<filter>quarter</filter>" +
								"<filter>year</filter>" +
							"</dependencies>" +
						"</datasource>" +
					"</filter>" +
					"<filter>" +
						"<name>quarter</name>" +
					"</filter>" +
					"<filter>" +
						"<name>year</name>" +
					"</filter>" +
				"</filters>" +
			"</dashboard>");
		
		InputSource source = new InputSource(reader);
		
		DashBoardDefinition dashBoardDefinition = serializer.DeSerealize(source);
		
		// Connection
		assertEquals("cubeA", dashBoardDefinition.getConnection().getCubeAlias());
		assertEquals("dbA", dashBoardDefinition.getConnection().getDataBaseAlias());
		assertEquals("templateA", dashBoardDefinition.getConnection().getTemplate());
		
		// Dashboard look
		assertEquals("cols", dashBoardDefinition.getLookDefinition().getProperties().get(0).getName());
		assertEquals(2, dashBoardDefinition.getLookDefinition().getProperties().get(0).getValue());
		
		// View 1
		ViewDefinition viewDefinition1 = dashBoardDefinition.getViews().get(0);
		assertEquals("view1", viewDefinition1.getName());
		assertEquals("query view 1", viewDefinition1.getDataSourceDefinition().getQuery());
		assertEquals("cubeA", viewDefinition1.getDataSourceDefinition().getConnection().getCubeAlias());
		assertEquals("dbA", viewDefinition1.getDataSourceDefinition().getConnection().getDataBaseAlias());
		assertEquals("templateA", viewDefinition1.getDataSourceDefinition().getConnection().getTemplate());
		
		// Dependencies 1
		assertEquals(2, viewDefinition1.getDataSourceDefinition().getDependencies().size());
		assertEquals("month", viewDefinition1.getDataSourceDefinition().getDependencies().get(0));
		assertEquals("brand", viewDefinition1.getDataSourceDefinition().getDependencies().get(1));
		
		// look 1
		assertEquals("lookType", viewDefinition1.getLookDefinition().getProperties().get(0).getName());
		assertEquals("ext.grid", viewDefinition1.getLookDefinition().getProperties().get(0).getValue());
		
		assertEquals("width", viewDefinition1.getLookDefinition().getProperties().get(1).getName());
		assertEquals(400, viewDefinition1.getLookDefinition().getProperties().get(1).getValue());
		
		assertEquals("height", viewDefinition1.getLookDefinition().getProperties().get(2).getName());
		assertEquals(200, viewDefinition1.getLookDefinition().getProperties().get(2).getValue());
		
		// fields
		assertEquals("fields", viewDefinition1.getLookDefinition().getProperties().get(3).getName());
		List<String> fields = (List<String>) viewDefinition1.getLookDefinition().getProperties().get(3).getValue();
		assertEquals("Product", fields.get(0));
		assertEquals("SalePrice", fields.get(1));
		assertEquals("Quantity", fields.get(2));
		
		// columns
		assertEquals("columns", viewDefinition1.getLookDefinition().getProperties().get(4).getName());
		List<GenericDefinition> columns = (List<GenericDefinition>) viewDefinition1.getLookDefinition().getProperties().get(4).getValue();
		GenericDefinition column2 = columns.get(1);
		assertEquals("header", column2.getProperties().get(0).getName());
		assertEquals("Precio Venta $", column2.getProperties().get(0).getValue());
		assertEquals("dataIndex", column2.getProperties().get(1).getName());
		assertEquals("SalePrice", column2.getProperties().get(1).getValue());
		
		// serie
		assertEquals("series", viewDefinition1.getLookDefinition().getProperties().get(5).getName());
		List<GenericDefinition> series = (List<GenericDefinition>) viewDefinition1.getLookDefinition().getProperties().get(5).getValue();
		GenericDefinition serie = series.get(0);
		
		assertEquals("type", serie.getProperties().get(0).getName());
		assertEquals("line", serie.getProperties().get(0).getValue());
		assertEquals("displayName", serie.getProperties().get(1).getName());
		assertEquals("Cantidad", serie.getProperties().get(1).getValue());
		assertEquals("yField", serie.getProperties().get(2).getName());
		assertEquals("Quantity", serie.getProperties().get(2).getValue());
		
		GenericDefinition serieLabel = serie.getProperty("label");
		assertNotNull("falta el label", serieLabel);
		assertEquals("field", serieLabel.getProperties().get(0).getName());
		assertEquals("Product", serieLabel.getProperties().get(0).getValue());
		
		// axes 1
		assertEquals("axes", viewDefinition1.getLookDefinition().getProperties().get(6).getName());
		List<GenericDefinition> axes = (List<GenericDefinition>) viewDefinition1.getLookDefinition().getProperties().get(6).getValue();
		GenericDefinition axe1 = axes.get(0);
		
		assertEquals("fields", axe1.getProperties().get(2).getName());
		
		// axes 2
		GenericDefinition axe2fields = axes.get(1).getProperties().get(2);
		assertEquals("fields", axe2fields.getName());
		
		List<GenericDefinition> axe2fieldsList = (List<GenericDefinition>) axe2fields.getValue();
		assertEquals(1, axe2fieldsList.size());
		
		// position
		GenericDefinition legend = viewDefinition1.getLookDefinition().getProperties().get(7);
		assertEquals("legend", legend.getName());
		
		GenericDefinition legendValue = (GenericDefinition)legend.getValue();
		assertEquals(1, legendValue.getProperties().size());
		assertEquals("position", legendValue.getProperties().get(0).getName());
		assertEquals("right", legendValue.getProperties().get(0).getValue());
		
		// View 2
		ViewDefinition viewDefinition2 = dashBoardDefinition.getViews().get(1);
		assertEquals("view2", viewDefinition2.getName());
		assertEquals("query view 2", viewDefinition2.getDataSourceDefinition().getQuery());
		assertEquals("lookType", viewDefinition2.getLookDefinition().getProperties().get(0).getName());
		assertEquals("ext.graph", viewDefinition2.getLookDefinition().getProperties().get(0).getValue());
		
		assertEquals("cubeB", viewDefinition2.getDataSourceDefinition().getConnection().getCubeAlias());
		assertEquals("dbB", viewDefinition2.getDataSourceDefinition().getConnection().getDataBaseAlias());
		assertEquals("templateB", viewDefinition2.getDataSourceDefinition().getConnection().getTemplate());
		
		// Filter brand
		FilterDefinition filterBrand = dashBoardDefinition.getFilters().get(0);
		assertEquals("brand", filterBrand.getName());
		assertEquals("Marca", filterBrand.getLabel());
		assertEquals("WITH MEMBER [Measures].[one] AS '1' SELECT {[Measures].[one]} ON COLUMNS, {[Brand].members} on rows FROM [Sales]", filterBrand.getDataSourceDefinition().getQuery());
		assertEquals("cubeC", filterBrand.getDataSourceDefinition().getConnection().getCubeAlias());
		assertEquals("dbC", filterBrand.getDataSourceDefinition().getConnection().getDataBaseAlias());
		assertEquals(0, filterBrand.getDependants().size());
		
		// Filter month
		FilterDefinition filterMonth = dashBoardDefinition.getFilters().get(1);
		assertEquals("month", filterMonth.getName());
		assertEquals("Mes", filterMonth.getLabel());
		assertEquals("WITH MEMBER [Measures].[one] AS '1' SELECT {[Measures].[one]} ON COLUMNS, {[Time].members} on rows FROM [Sales]", filterMonth.getDataSourceDefinition().getQuery());
		assertEquals("cubeA", filterMonth.getDataSourceDefinition().getConnection().getCubeAlias());
		assertEquals("dbA", filterMonth.getDataSourceDefinition().getConnection().getDataBaseAlias());
		assertEquals("templateA", filterMonth.getDataSourceDefinition().getConnection().getTemplate());
		
		assertEquals(2, filterMonth.getDataSourceDefinition().getDependencies().size());
		assertEquals("quarter", filterMonth.getDataSourceDefinition().getDependencies().get(0));
		assertEquals("year", filterMonth.getDataSourceDefinition().getDependencies().get(1));
		assertEquals(0, filterMonth.getDependants().size());
		
		// Filter quarter
		FilterDefinition filterQuarter = dashBoardDefinition.getFilters().get(2);
		assertEquals(2, filterQuarter.getDependants().size());
		assertEquals("brand", filterQuarter.getDependants().get(0));
		assertEquals("month", filterQuarter.getDependants().get(1));
		
		// Filter year
		FilterDefinition filterYear = dashBoardDefinition.getFilters().get(3);
		assertEquals(1, filterYear.getDependants().size());
		assertEquals("month", filterYear.getDependants().get(0));
	}

}
