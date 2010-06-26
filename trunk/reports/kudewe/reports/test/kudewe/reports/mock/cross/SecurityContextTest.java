package kudewe.reports.mock.cross;

import kudewe.reports.cross.SecurityContext;
import kudewe.reports.model.Tenant;

public class SecurityContextTest implements SecurityContext {

	@Override
	public Tenant getTenant() {
		Tenant tenant = new Tenant();
		tenant.setAlias("test");
		return tenant;
	}

}
