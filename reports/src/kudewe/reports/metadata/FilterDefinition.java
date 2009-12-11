package kudewe.reports.metadata;

import java.util.ArrayList;
import java.util.List;

public class FilterDefinition {
	
	/**
	 * The filter's data source.
	 */
	private DataSourceDefinition dataSourceDefinition;
	
	/**
	 * The filter's name 
	 */
	private String name;

	/**
	 * The filter's label 
	 */
	private String label;
	
	/**
	 * Filter's dependants
	 */
	private List<String> dependants = new ArrayList<String>();
	
	/**
	 * The view's dash board
	 */
	private DashBoardDefinition dashBoardDefinition;
	
	/**
	 * Return filter's url. Identify filter as url address
	 * @return
	 */
	public String getUrl() {
		return dashBoardDefinition.getUrl() + "/filter/" + name;
	}
	
	/**
	 * Return filter's data source.
	 * @return
	 */
	public DataSourceDefinition getDataSourceDefinition() {
		return dataSourceDefinition;
	}

	/**
	 * Set filter's data source.
	 * @param dataSourceDefinition
	 */
	public void setDataSourceDefinition(DataSourceDefinition dataSourceDefinition) {
		this.dataSourceDefinition = dataSourceDefinition;
		if(this.dashBoardDefinition != null) {
			this.dataSourceDefinition.setDashBoard(this.dashBoardDefinition);
		}
	}

	/**
	 * Return filter's name
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set filter's name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Return filter's label
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Set filter's label
	 * @param name
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Return filter's dash board
	 */
	public DashBoardDefinition getDashBoardDefinition() {
		return dashBoardDefinition;
	}

	/**
	 * Set filter's dash board
	 */
	public void setDashBoardDefinition(DashBoardDefinition dashBoard) {
		this.dashBoardDefinition = dashBoard;
		if (this.dataSourceDefinition != null) {
			this.dataSourceDefinition.setDashBoard(dashBoard);
		}
	}
	
	/**
	 * Get filter's dependencies
	 */
	public List<String> getDependants() {
		return dependants;
	}
	
}
