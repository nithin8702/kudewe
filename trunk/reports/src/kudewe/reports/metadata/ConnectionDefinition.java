package kudewe.reports.metadata;

public class ConnectionDefinition {
	/**
	 * The connection string 
	 */
	private String connectionString;

	/**
	 * The cube's alias 
	 */
	private String cubeAlias;

	/**
	 * The database alias 
	 */
	private String dataBaseAlias;
	
	/**
	 * Return connection string
	 * @return
	 */
	public String getConnectionString() {
		return connectionString;
	}

	/**
	 * Set connection string
	 * @param name
	 */
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}
	
	/**
	 * return cube alias
	 * @return
	 */
	public String getCubeAlias() {
		return cubeAlias;
	}

	/**
	 * Set cube alias
	 * @param cubeAlias
	 */
	public void setCubeAlias(String cubeAlias) {
		this.cubeAlias = cubeAlias;
	}

	/**
	 * Return database alias
	 * @return
	 */
	public String getDataBaseAlias() {
		return dataBaseAlias;
	}

	/**
	 * Set database alias
	 * @param dataBaseAlias
	 */
	public void setDataBaseAlias(String dataBaseAlias) {
		this.dataBaseAlias = dataBaseAlias;
	}

}
