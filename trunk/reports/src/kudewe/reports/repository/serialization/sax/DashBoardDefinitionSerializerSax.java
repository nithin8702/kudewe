package kudewe.reports.repository.serialization.sax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kudewe.reports.metadata.ConnectionDefinition;
import kudewe.reports.metadata.DashBoardDefinition;
import kudewe.reports.metadata.DataSourceDefinition;
import kudewe.reports.metadata.FilterDefinition;
import kudewe.reports.metadata.GenericDefinition;
import kudewe.reports.metadata.ViewDefinition;
import kudewe.reports.repository.serialization.Serializer;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


public class DashBoardDefinitionSerializerSax extends DefaultHandler implements Serializer<DashBoardDefinition, InputSource> {
	
	private DashBoardDefinition dashBoardDefinition;
	private ViewDefinition viewDefinition;
	private FilterDefinition filterDefinition;
	private GenericDefinition genericDefinition;
	private String genericDefinitionType;
	private GenericDefinition genericDefinition2;
	private StringBuilder text;
	private List<String> path = new ArrayList<String>();
	
	@Override
	public DashBoardDefinition DeSerealize(InputSource media) {
		DashBoardDefinitionSerializerSax handler = new DashBoardDefinitionSerializerSax();
		
		try {
			XMLReader xr = XMLReaderFactory.createXMLReader();
			xr.setContentHandler(handler);
			xr.setErrorHandler(handler);
			xr.parse(media);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buildFilterDependants(handler.getDashBoardDefinition());
	}
	
	/**
	 * Build filter's dependants for each filter in dashboard
	 * @param dashBoardDefinition
	 * @return
	 */
	private DashBoardDefinition buildFilterDependants(DashBoardDefinition dashBoardDefinition) {
		for (FilterDefinition filter : dashBoardDefinition.getFilters()) {
			DataSourceDefinition dataSourceDefinition = filter.getDataSourceDefinition();
			if (dataSourceDefinition != null) {
				for (String filterName : filter.getDataSourceDefinition().getDependencies()) {
					dashBoardDefinition.getFilter(filterName).getDependants().add(filter.getName());
				}
			}
		}
		return dashBoardDefinition;
	}
	
	@Override
	public void startElement (String uri, String name, String qName, Attributes atts) {
		path.add(name);
		
		if ("dashboard".equals(name)) {
			dashBoardDefinition = new DashBoardDefinition();
		} else if ("view".equals(name)) {
			viewDefinition = new ViewDefinition();
		} else if ("filter".equals(name) && "filters".equals(getAncestorNodeName(1))) {
			filterDefinition = new FilterDefinition();
		} else if ("look".equals(name)) {
			
			// Create look in function of parent node
			if ("view".equals(getAncestorNodeName(1))) {
				// parser is on view
				viewDefinition.setLookDefinition(new GenericDefinition());
			}
		} else if ("datasource".equals(name)) {
			
			// Create data source in function of parent node
			if ("view".equals(getAncestorNodeName(1))) {
				// parser is on view
				viewDefinition.setDataSourceDefinition(new DataSourceDefinition());
			} else if ("filter".equals(getAncestorNodeName(1))) {
				// parser is on filter
				filterDefinition.setDataSourceDefinition(new DataSourceDefinition());
			}
			
		} else if ("connection".equals(name)) {
			
			// Create connection in function of parent node
			if ("dashboard".equals(getAncestorNodeName(1))) {
				// parser is on dashboard
				dashBoardDefinition.setConnection(new ConnectionDefinition());
			} else if ("view".equals(getAncestorNodeName(2))) {
				// parser is on view/datasource
				viewDefinition.getDataSourceDefinition().setConnection(new ConnectionDefinition());
			} else if ("filter".equals(getAncestorNodeName(2))) {
				// parser is on filter/datasource
				filterDefinition.getDataSourceDefinition().setConnection(new ConnectionDefinition());
			}
		} else {
			// Create look in function of parent node
			
			
			if ("look".equals(getAncestorNodeName(1))) {
				// parser is on look
				genericDefinition = new GenericDefinition();
				genericDefinition.setName(name);
				genericDefinitionType = atts.getValue("type");
			} else if ("look".equals(getAncestorNodeName(2))) {
				// parser is on look/x
				genericDefinition2 = new GenericDefinition();
				genericDefinition2.setName(name);
			}
		}
		
		text = new StringBuilder();
	}
	
	@Override
	public void endElement (String uri, String name, String qName) {
		if ("view".equals (name)) {
			dashBoardDefinition.addView(viewDefinition);
		} else if ("filter".equals (name) && "filters".equals(getAncestorNodeName(1))) {
			dashBoardDefinition.addFilter(filterDefinition);
		} else if ("filter".equals(name) && "dependencies".equals(getAncestorNodeName(1)) && "filter".equals(getAncestorNodeName(3))) {
			filterDefinition.getDataSourceDefinition().getDependencies().add(text.toString());
		} else if ("filter".equals(name) && "dependencies".equals(getAncestorNodeName(1)) && "view".equals(getAncestorNodeName(3))) {
			viewDefinition.getDataSourceDefinition().getDependencies().add(text.toString());
		} else if ("name".equals (name)) {
			
			// Set name in function of parent node
			if ("filter".equals(getAncestorNodeName(1))) {
				// parser is on filter
				filterDefinition.setName(text.toString());
			} else if ("view".equals(getAncestorNodeName(1))) {
				// parser is on view
				viewDefinition.setName(text.toString());
			}
		
		} else if ("label".equals (name)) {
			
			// Set name in function of parent node
			if ("filter".equals(getAncestorNodeName(1))) {
				// parser is on filter
				filterDefinition.setLabel(text.toString());
			}
		} else if ("query".equals(name)) {
			
			// Set query in function of parent node
			if ("view".equals(getAncestorNodeName(2))) {
				// parser is on view/datasource
				viewDefinition.getDataSourceDefinition().setQuery(text.toString());
			} else if ("filter".equals(getAncestorNodeName(2))) {
				// parser is on filter/datasource
				filterDefinition.getDataSourceDefinition().setQuery(text.toString());
			}

		} else if ("cubeAlias".equals(name)) {
			getConnectionDefinition().setCubeAlias(text.toString());
		} else if ("dataBaseAlias".equals(name)) {
			getConnectionDefinition().setDataBaseAlias(text.toString());
		} else if ("template".equals(name)) {
			getConnectionDefinition().setTemplate(text.toString());
		} else {
			if ("look".equals(getAncestorNodeName(1))) {
				// parser is on look
				if (genericDefinition.getProperties().size() == 0) {
					genericDefinition.setValue(getGenericDefinitionValue(text.toString()));
					viewDefinition.getLookDefinition().addProperty(genericDefinition, genericDefinitionType);
				} else {
					viewDefinition.getLookDefinition().addProperty(new GenericDefinition(name, genericDefinition), genericDefinitionType);
				}
				
			} else if ("look".equals(getAncestorNodeName(2))) {
				// parser is on look/x
				genericDefinition2.setValue(getGenericDefinitionValue(text.toString()));
				genericDefinition.addProperty(genericDefinition2, null);
			}
		
		}
		path.remove(path.size() - 1);
	}

	private ConnectionDefinition getConnectionDefinition() {
		ConnectionDefinition connectionDefinition = null;
		
		// Set connection string in function of parent node
		if ("dashboard".equals(getAncestorNodeName(2))) {
			// parser is on dashboard/connection
			connectionDefinition = dashBoardDefinition.getConnection();
		} else if ("view".equals(getAncestorNodeName(3))) {
			// parser is on view/datasource.connection
			connectionDefinition =  viewDefinition.getDataSourceDefinition().getConnection();
		} else if ("filter".equals(getAncestorNodeName(3))) {
			// parser is on filter/datasource.connection
			connectionDefinition = filterDefinition.getDataSourceDefinition().getConnection();
		}
		return connectionDefinition;
	}
	
	private Object getGenericDefinitionValue(String valueString) {
		Object value;
		Integer valueInt = null;
		
		try {
			valueInt = Integer.parseInt(valueString);
	    } catch(NumberFormatException e) {
	    }
		
		if (valueInt != null) {
			value = valueInt;
		} else {
			value = valueString;
		}
		return value;
	}
	
	@Override
    public void characters (char ch[], int start, int length) {
    	text.append(new String(ch, start, length));
    }
	
	/**
	 * Returns parsed view definition
	 * @return
	 */
	private DashBoardDefinition getDashBoardDefinition() {
		return dashBoardDefinition;
	}

	private String getAncestorNodeName(int level) {
		String name = null;
		if (path.size() - 1 - level >= 0) {
			name = path.get(path.size() - 1 - level);
		}
		return name;
	}
}
