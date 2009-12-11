package kudewe.reports.metadata;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GenericDefinition {
	private String name;
	private Object value;
	private Map<String, GenericDefinition> properties = new LinkedHashMap<String, GenericDefinition>();
	
	public GenericDefinition() {
	
	}
	
	public GenericDefinition(String name, Object value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public List<GenericDefinition> getProperties() {
		return new ArrayList<GenericDefinition>(properties.values());
	}
	public GenericDefinition getProperty(String name) {
		return properties.get(name);
	}
	
	public void addProperty(GenericDefinition property) {
		addProperty(property, null);
	}
	
	@SuppressWarnings("unchecked")
	public void addProperty(GenericDefinition property, String type) {
		if (properties.containsKey(property.getName())) {
			Object oldValue = properties.get(property.getName()).getValue();
			GenericDefinition newProperty;
			List<Object> newValue;
			
			if (!List.class.isAssignableFrom(oldValue.getClass())) {
				newValue = new ArrayList<Object>();
				newValue.add(oldValue);
			} else {
				newValue = (List<Object>) oldValue; 
			}
			
			newValue.add(property.getValue());
			newProperty = new GenericDefinition(property.getName(), newValue);
			
			properties.put(newProperty.getName(), newProperty);
		} else if ("list".equals(type)) {
			List<Object> newValue = new ArrayList<Object>();
			newValue.add(property.getValue());
			GenericDefinition newProperty = new GenericDefinition(property.getName(), newValue);
			properties.put(newProperty.getName(), newProperty);
		} else {
			properties.put(property.getName(), property);
		}
	}
}
