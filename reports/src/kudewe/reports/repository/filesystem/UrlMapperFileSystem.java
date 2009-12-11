package kudewe.reports.repository.filesystem;

import kudewe.reports.cross.SecurityContext;
import kudewe.reports.repository.UrlMapper;

public class UrlMapperFileSystem implements UrlMapper {
	private final String filterToken = "/filter/";
	private final String viewToken = "/view/";
	
	/**
	 * Repository base path
	 */
	private String repositoryBasePath;
	
	/**
	 * Security context
	 */
	private SecurityContext securityContext;

	/**
	 * Setter injection
	 * Repository base path. Ends with "/"
	 * @param urlMapper
	 */
	public void setRepositoryBasePath(String repositoryBasePath) {
		this.repositoryBasePath = repositoryBasePath;
	}
	
	/**
	 * Setter injection
	 * Security context
	 * @param securityContext
	 */
	public void setSecurityContext(SecurityContext securityContext) {
		this.securityContext = securityContext;
	}
	
	@Override
	public String GetDashBoardDefinitionPath(String url) {
		return getBasePath() + GetDashBoardDefinitionUrl(url) + ".xml";
	}
	
	/**
	 * Retuns base path appling tenant replace 
	 * @return
	 */
	private String getBasePath() {
		return repositoryBasePath.replaceFirst("\\$\\{tenant\\}", securityContext.getTenant().getAlias());
	}
	
	@Override
	public String GetDashBoardDefinitionUrl(String url) {
		String dashBoardDefinitionUrl;
		
		if (url.contains(filterToken)){
			dashBoardDefinitionUrl = url.substring(0, url.indexOf(filterToken));
		} else if (url.contains(viewToken)){
			dashBoardDefinitionUrl = url.substring(0, url.indexOf(viewToken));
		} else {
			dashBoardDefinitionUrl = url;
		}
		
		return dashBoardDefinitionUrl;
	}

	@Override
	public String GetFilterName(String url) {
		return url.substring(url.indexOf(filterToken) + filterToken.length());
	}

	@Override
	public String GetViewName(String url) {
		return url.substring(url.indexOf(viewToken) + viewToken.length());
	}
	
	@Override
	public String GetMenuDefinitionPath(String url) {
		return getBasePath() + url + ".xml";
	}
}
