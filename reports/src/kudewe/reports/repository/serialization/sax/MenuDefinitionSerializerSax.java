package kudewe.reports.repository.serialization.sax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kudewe.reports.metadata.MenuItemDefinition;
import kudewe.reports.repository.serialization.Serializer;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


public class MenuDefinitionSerializerSax extends DefaultHandler implements Serializer<List<MenuItemDefinition>, InputSource> {
	
	private List<MenuItemDefinition> menuDefinition = new ArrayList<MenuItemDefinition>();
	private MenuItemDefinition menuItemDefinition1;
	private MenuItemDefinition menuItemDefinition2;
	private MenuItemDefinition currentMenuItemDefinition;
	private StringBuilder text;
	private List<String> path = new ArrayList<String>();
	
	@Override
	public List<MenuItemDefinition> DeSerealize(InputSource media) {
		MenuDefinitionSerializerSax handler = new MenuDefinitionSerializerSax();
		
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
		return handler.getMenuDefinition();
	}
	
	@Override
	public void startElement (String uri, String name, String qName, Attributes atts) {
		
		path.add(name);
		
		if("menu".equals(name)) {
			if (getLevelDepth() == 2) {
				menuItemDefinition1 = new MenuItemDefinition();
				currentMenuItemDefinition = menuItemDefinition1;
			} else if (getLevelDepth() == 4) {
				menuItemDefinition2 = new MenuItemDefinition();
				currentMenuItemDefinition = menuItemDefinition2;
			}
		}
		
		text = new StringBuilder();
	}
	
	@Override
	public void endElement (String uri, String name, String qName) {
		if ("name".equals(name)) {
			currentMenuItemDefinition.setName(text.toString());
		} else if ("dashBoardUrl".equals(name)) {
			currentMenuItemDefinition.setDashBoardUrl(text.toString());
		} else if ("menu".equals(name)) {
			if (getLevelDepth() == 2) {
				menuDefinition.add(menuItemDefinition1);
			} else if (getLevelDepth() == 4) {
				menuItemDefinition1.addItem(menuItemDefinition2);
			}
		}
		path.remove(name);
	}
	
	@Override
    public void characters (char ch[], int start, int length) {
    	text.append(new String(ch, start, length));
    }
	
	/**
	 * Returns parsed view definition
	 * @return
	 */
	private List<MenuItemDefinition> getMenuDefinition() {
		return menuDefinition;
	}

	private int getLevelDepth() {
		return path.size();
	}

}
