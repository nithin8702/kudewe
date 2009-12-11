package kudewe.reports.cross.spring;

import kudewe.reports.cross.SecurityContext;
import kudewe.reports.model.Tenant;

import org.springframework.security.context.SecurityContextHolder;


public class SecurityContextSpring implements SecurityContext {

	@Override
	public Tenant getTenant() {
		Tenant tenant = new Tenant();
		tenant.setAlias(SecurityContextHolder.getContext().getAuthentication().getName());
		return tenant;
	}

}
