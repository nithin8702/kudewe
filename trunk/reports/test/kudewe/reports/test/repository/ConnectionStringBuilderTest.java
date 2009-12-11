package kudewe.reports.test.repository;

import static org.junit.Assert.assertEquals;
import kudewe.reports.metadata.ConnectionDefinition;
import kudewe.reports.repository.ConnectionStringBuilder;
import kudewe.reports.test.common.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class ConnectionStringBuilderTest extends BaseTest {
	@Autowired
	private ConnectionStringBuilder connectionStringBuilder;
	
	@Test
	public void GetConnectionString() {
		ConnectionDefinition connectionDefinition = new ConnectionDefinition();
		connectionDefinition.setCubeAlias("cube_sales");
		connectionDefinition.setDataBaseAlias("testdb");
		
		assertEquals("Provider=mondrian;Catalog=/home/fer/workspace/kudewe/data/test/mondrian/cube_sales.xml;JdbcDrivers=org.hsqldb.jdbcDriver;Jdbc=Jdbc:hsqldb:file:/home/fer/workspace/kudewe/data/test/hsqldb/testdb;jdbcUser=sa;jdbcPassword=;", connectionStringBuilder.buildConnectionString(connectionDefinition));
	}
}
