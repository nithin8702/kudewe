package kudewe.reports.model;

/**
 * Represent a item of a filter
 * @author fer
 *
 */
public class FilterItem {
	/**
	 * The filter item's value
	 */
	private String value;
	
	/**
	 * The filter item's description
	 */
	private String description;
	
	/**
	 * Return the filter item's value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Return the filter item's description
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Constructor
	 * @param value Item value
	 * @param description Item description
	 */
	public FilterItem(String value, String description) {
		this.value = value;
		this.description = description;
	}
}
