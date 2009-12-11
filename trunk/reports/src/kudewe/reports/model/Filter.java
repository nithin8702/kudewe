package kudewe.reports.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a filter of dashboard panel
 * @author fer
 *
 */
public class Filter {
	/**
	 * The filter's name
	 */
	private String name;
	
	/**
	 * The filter's items
	 */
	private List<FilterItem> items = new ArrayList<FilterItem>();
	
	/**
	 * The filter's value
	 */
	private String selectedValue;
	
	/**
	 * The filter's default value
	 */
	private String defaultValue;
	
	/**
	 * The filter's parent for a hierarchy
	 */
	private Filter parent;
	
	/**
	 * Return the filter's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Return filter's items 
	 */
	public List<FilterItem> getItems() {
		return items;
	}

	/**
	 * Return the filter's selected item
	 */
	public String getSelectedValue() {
		// Set current selection
		String selectedValue = this.selectedValue;
		
		// If does not has current selection, set default selection
		if (selectedValue == null && defaultValue != null) {
			selectedValue = defaultValue;
		}
		
		// If does not has selection, set parent selection
		if (selectedValue == null && parent != null) {
			selectedValue = parent.getSelectedValue();
		} else if (selectedValue != null 
				&& parent != null && parent.getSelectedValue() != null) {
			// If has selection and parent has selection too
			if (!selectedValue.contains(parent.getSelectedValue())) {
				selectedValue = parent.getSelectedValue();
			}
		}
		
		return selectedValue;
	}
	
	/**
	 * Constructor
	 * @param filterName Filter name
	 */
	public Filter(String filterName) {
		this.name = filterName;
	}
	
	/**
	 * Constructor
	 * @param filterName Filter name
	 * @param selectedValue Selected value
	 */
	public Filter(String filterName, String selectedValue) {
		this.name = filterName;
		this.selectedValue = selectedValue;
	}
	
	/**
	 * Set default value
	 * @param value
	 * @return
	 */
	public Filter defaultValue(String value) {
		this.defaultValue = value;
		return this;
	}
	
	/**
	 * Set parent filter
	 * @param parent
	 * @return
	 */
	public Filter parent(Filter parent) {
		this.parent = parent;
		return this.parent;
	}
}
