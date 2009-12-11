package kudewe.reports.repository.olap;

import kudewe.reports.cross.SecurityContext;
import kudewe.reports.metadata.ConnectionDefinition;
import kudewe.reports.repository.ConnectionStringBuilder;

public class ConnectionStringBuilderOlap implements ConnectionStringBuilder {
	private String template;
	private SecurityContext securityContext;
	
	public void setTemplate(String template) {
		this.template = template;
	}

	public void setSecurityContext(SecurityContext securityContext) {
		this.securityContext = securityContext;
	}
	
	@Override
	public String buildConnectionString(
			ConnectionDefinition connectionDefinition) {
		return template.replaceFirst("\\$\\{cubeAlias\\}", connectionDefinition.getCubeAlias())
				.replaceFirst("\\$\\{dataBaseAlias\\}", connectionDefinition.getDataBaseAlias())
				.replaceAll("\\$\\{tenant\\}", securityContext.getTenant().getAlias());
	}
}
