package kudewe.reports.repository;

import kudewe.reports.metadata.ConnectionDefinition;

public interface ConnectionStringBuilder {
	public String buildConnectionString(ConnectionDefinition connectionDefinition);
}
