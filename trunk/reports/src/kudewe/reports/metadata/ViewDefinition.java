package kudewe.reports.metadata;

/**
 * Defines a view
 * @author fer
 *
 */
public class ViewDefinition {
	
	/**
	 * The view's name. Identify view as url address 
	 */
	private String name;
	
	/**
	 * The view's data source.
	 */
	private DataSourceDefinition dataSourceDefinition;
	
	/**
	 * The view's dash board
	 */
	private DashBoardDefinition dashBoardDefinition;
	
	/**
	 * The view's look
	 */
	private GenericDefinition lookDefinition;
	
	/**
	 * Return view's url. Identify view as url address
	 * @return
	 */
	public String getUrl() {
		return dashBoardDefinition.getUrl() + "/view/" + name;
	}
	
	/**
	 * Return view's name.
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set view's name.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return view's data source.
	 * @return
	 */
	public DataSourceDefinition getDataSourceDefinition() {
		return dataSourceDefinition;
	}

	/**
	 * Set view's data source.
	 * @param dataSourceDefinition
	 */
	public void setDataSourceDefinition(DataSourceDefinition dataSourceDefinition) {
		this.dataSourceDefinition = dataSourceDefinition;
		if(this.dashBoardDefinition != null) {
			this.dataSourceDefinition.setDashBoard(this.dashBoardDefinition);
		}
	}

	/**
	 * Set view's dash board
	 */
	public DashBoardDefinition getDashBoardDefinition() {
		return dashBoardDefinition;
	}

	/**
	 * Return view's dash board
	 */
	public void setDashBoardDefinition(DashBoardDefinition dashBoard) {
		this.dashBoardDefinition = dashBoard;
		if (this.dataSourceDefinition != null) {
			this.dataSourceDefinition.setDashBoard(dashBoard);
		}
	}

	/**
	 * Return view's look
	 */
	public GenericDefinition getLookDefinition() {
		return lookDefinition;
	}

	/**
	 * Set view's look
	 */
	public void setLookDefinition(GenericDefinition lookDefinition) {
		this.lookDefinition = lookDefinition;
	}

}
