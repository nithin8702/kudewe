package kudewe.reports.test.metadata;

import java.util.List;

import junit.framework.Assert;
import kudewe.reports.metadata.GenericDefinition;
import kudewe.reports.test.common.BaseTest;

import org.junit.Test;


public class GenericDefinitionTest extends BaseTest {
	@SuppressWarnings("unchecked")
	@Test
	public void addProperty() {
		GenericDefinition fauvorites = new GenericDefinition();
		fauvorites.addProperty(new GenericDefinition("title", "Harry Potter"));
		fauvorites.addProperty(new GenericDefinition("title", "Star Wars"));
		fauvorites.addProperty(new GenericDefinition("food", "bread"));
		
		GenericDefinition pet1 = new GenericDefinition();
		pet1.addProperty(new GenericDefinition("name", "luca"));
		pet1.addProperty(new GenericDefinition("type", "can"));
		fauvorites.addProperty(new GenericDefinition("pets", pet1));
		
		GenericDefinition pet2 = new GenericDefinition();
		pet2.addProperty(new GenericDefinition("name", "niceto"));
		pet2.addProperty(new GenericDefinition("type", "can"));
		fauvorites.addProperty(new GenericDefinition("pets", pet2));
		
		Assert.assertEquals("title", fauvorites.getProperties().get(0).getName());
		List<Object> title = (List<Object>) fauvorites.getProperties().get(0).getValue();
		Assert.assertEquals("Harry Potter", (String) title.get(0));
		Assert.assertEquals("Star Wars", (String) title.get(1));
		
		Assert.assertEquals("food", fauvorites.getProperties().get(1).getName());
		Assert.assertEquals("bread", fauvorites.getProperties().get(1).getValue());
		
		List<GenericDefinition> pets = (List<GenericDefinition>) fauvorites.getProperties().get(2).getValue();
		Assert.assertEquals("name", pets.get(0).getProperties().get(0).getName());
		Assert.assertEquals("luca", pets.get(0).getProperties().get(0).getValue());
		Assert.assertEquals("type", pets.get(0).getProperties().get(1).getName());
		Assert.assertEquals("can", pets.get(0).getProperties().get(1).getValue());
		
		Assert.assertEquals("name", pets.get(1).getProperties().get(0).getName());
		Assert.assertEquals("niceto", pets.get(1).getProperties().get(0).getValue());
		Assert.assertEquals("type", pets.get(1).getProperties().get(1).getName());
		Assert.assertEquals("can", pets.get(1).getProperties().get(1).getValue());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void addPropertyList() {
		GenericDefinition fauvorites = new GenericDefinition();
		fauvorites.addProperty(new GenericDefinition("title", "Harry Potter"), "list");
		
		List<Object> title = (List<Object>) fauvorites.getProperties().get(0).getValue();
		Assert.assertEquals("Harry Potter", (String) title.get(0));
	}
	
	@Test
	public void getProperty() {
		GenericDefinition fauvorites = new GenericDefinition();
		fauvorites.addProperty(new GenericDefinition("title", "Harry Potter"), "list");
	}
}
