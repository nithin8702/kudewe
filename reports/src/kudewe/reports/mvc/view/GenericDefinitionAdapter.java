package kudewe.reports.mvc.view;

import java.util.ArrayList;
import java.util.List;

import kudewe.reports.metadata.GenericDefinition;


public class GenericDefinitionAdapter {
	private GenericDefinition genericDefinition;
	
	public GenericDefinitionAdapter(GenericDefinition genericDefinition) {
		this.genericDefinition = genericDefinition;
	}
	
	@SuppressWarnings("unchecked")
	public Object getValue() {
		Object value;
		
		if (isComplexArray(genericDefinition.getValue())) {
			List<GenericDefinition> oldList = (List<GenericDefinition>) genericDefinition.getValue();
			List<GenericDefinitionAdapter> newlist = new ArrayList<GenericDefinitionAdapter>();
			for (GenericDefinition genericDefinition : oldList) {
				newlist.add(new GenericDefinitionAdapter(genericDefinition));
			}
			value = newlist;
		} else {
			value = genericDefinition.getValue();
		}
		
		return value;
	}
	
	public Object getName() {
		return genericDefinition.getName();
	}
	
	public List<GenericDefinition> getSimpleProperties() {
		List<GenericDefinition> properties = new ArrayList<GenericDefinition>();
		
		for (GenericDefinition innerObject : genericDefinition.getProperties()) {
			if (innerObject.getValue() != null && 
				!List.class.isAssignableFrom(innerObject.getValue().getClass()) &&
				!GenericDefinition.class.isAssignableFrom(innerObject.getValue().getClass())
			) {
				properties.add(innerObject);
			}
		}
		return properties;
	}
	
	public List<GenericDefinitionAdapter> getComplexProperties() {
		List<GenericDefinitionAdapter> properties = new ArrayList<GenericDefinitionAdapter>();
		
		for (GenericDefinition innerObject : genericDefinition.getProperties()) {
			if (innerObject.getValue() != null && 
				GenericDefinition.class.isAssignableFrom(innerObject.getValue().getClass())) {
				GenericDefinition complexProperty = (GenericDefinition) innerObject.getValue();
				complexProperty.setName(innerObject.getName());
				properties.add(new GenericDefinitionAdapter(complexProperty));
			} else if (innerObject.getProperties().size() > 0) {
				GenericDefinition complexProperty = innerObject;
				complexProperty.setName(innerObject.getName());
				properties.add(new GenericDefinitionAdapter(complexProperty));
			}
		}
		return properties;
	}
	
	public List<GenericDefinition> getSimpleArrayProperties() {
		List<GenericDefinition> properties = new ArrayList<GenericDefinition>();
		
		for (GenericDefinition innerObject : genericDefinition.getProperties()) {
			if (isSimpleArray(innerObject.getValue())) {
				properties.add(innerObject);
			}
		}
		return properties;
	}
	
	public List<GenericDefinitionAdapter> getComplexArrayProperties() {
		List<GenericDefinitionAdapter> properties = new ArrayList<GenericDefinitionAdapter>();
		
		for (GenericDefinition innerObject : genericDefinition.getProperties()) {
			if (isComplexArray(innerObject.getValue())) {
				properties.add(new GenericDefinitionAdapter(innerObject));
			}
		}
		return properties;
	}
	
	private boolean isArray(Object object) {
		return (object != null 
				&& List.class.isAssignableFrom(object.getClass()));
	}
	
	private boolean isItemArrayComplex(List<Object> list) {
		boolean isComplex = false;
		if (list.size() > 0) {
			Object item = list.get(0);
			isComplex = GenericDefinition.class.isAssignableFrom(item.getClass());
		}
		return isComplex;
	}
	
	@SuppressWarnings("unchecked")
	private boolean isSimpleArray(Object object) {
		return (isArray(object) && !isItemArrayComplex((List<Object>) object));
	}
	
	@SuppressWarnings("unchecked")
	private boolean isComplexArray(Object object) {
		return (isArray(object) && isItemArrayComplex((List<Object>) object));
	}
}
