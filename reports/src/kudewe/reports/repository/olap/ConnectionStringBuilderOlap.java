package kudewe.reports.repository.olap;

import java.util.Map;

import kudewe.reports.cross.SecurityContext;
import kudewe.reports.metadata.ConnectionDefinition;
import kudewe.reports.repository.ConnectionStringBuilder;

public class ConnectionStringBuilderOlap implements ConnectionStringBuilder {
	private Map<String, String> templates;
	private SecurityContext securityContext;
	
	public void setTemplates(Map<String, String> templates) {
		this.templates = templates;
	}

	public void setSecurityContext(SecurityContext securityContext) {
		this.securityContext = securityContext;
	}
	
	@Override
	public String buildConnectionString(ConnectionDefinition connectionDefinition) {
		if (connectionDefinition.getTemplate() == null) {
			throw new IllegalArgumentException("You must specify a template in connectionDefinition");
		}
		if (!templates.containsKey(connectionDefinition.getTemplate())) {
			throw new IllegalArgumentException(connectionDefinition.getTemplate() + " is not a valid template");
		}
		String template = templates.get(connectionDefinition.getTemplate());
		
		return template.replaceFirst("\\$\\{cubeAlias\\}", connectionDefinition.getCubeAlias())
				.replaceFirst("\\$\\{dataBaseAlias\\}", connectionDefinition.getDataBaseAlias())
				.replaceAll("\\$\\{tenant\\}", securityContext.getTenant().getAlias());
	}
}
