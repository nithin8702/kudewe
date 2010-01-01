package kudewe.reports.metadata;

public class ConnectionDefinition {

	private String cubeAlias;
	private String dataBaseAlias;
	private String template;
	
	public String getCubeAlias() {
		return cubeAlias;
	}

	public void setCubeAlias(String cubeAlias) {
		this.cubeAlias = cubeAlias;
	}

	public String getDataBaseAlias() {
		return dataBaseAlias;
	}

	public void setDataBaseAlias(String dataBaseAlias) {
		this.dataBaseAlias = dataBaseAlias;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template= template;
	}

}
