package kudewe.reports.repository.filesystem;

import java.util.List;

import kudewe.reports.metadata.DashBoardDefinition;
import kudewe.reports.metadata.FilterDefinition;
import kudewe.reports.metadata.MenuItemDefinition;
import kudewe.reports.metadata.ViewDefinition;
import kudewe.reports.repository.MetadataRepository;
import kudewe.reports.repository.UrlMapper;
import kudewe.reports.repository.serialization.Serializer;

import org.xml.sax.InputSource;


/**
 * Implements MetadataRepository on local file system 
 * @author fer
 *
 */
public class MetadataRepositoryFileSystem implements MetadataRepository {
	/**
	 * Strategy for map abstract url to file 
	 */
	private UrlMapper urlMapper;
	
	/**
	 * Object used to deserealize dash board definition 
	 */
	private Serializer<DashBoardDefinition, InputSource> dashBoardDefinitionSerializerSax;
	
	/**
	 * Object used to deserealize menu definition 
	 */
	private Serializer<List<MenuItemDefinition>, InputSource> menuDefinitionSerializerSax;
	
	/**
	 * Setter injection
	 * @param urlMapper
	 */
	public void setUrlMapper(UrlMapper urlMapper) {
		this.urlMapper = urlMapper;
	}

	/**
	 * Setter injection
	 * @param dashBoardDefinitionSerializerSax
	 */
	public void setDashBoardDefinitionSerializerSax(
			Serializer<DashBoardDefinition, InputSource> dashBoardDefinitionSerializerSax) {
		this.dashBoardDefinitionSerializerSax = dashBoardDefinitionSerializerSax;
	}
	
	/**
	 * Setter injection
	 * @param dashBoardDefinitionSerializerSax
	 */
	public void setMenuDefinitionSerializerSax(
			Serializer<List<MenuItemDefinition>, InputSource> menuDefinitionSerializerSax) {
		this.menuDefinitionSerializerSax = menuDefinitionSerializerSax;
	}

	@Override
	public FilterDefinition getFilterDefinition(String url) {
		DashBoardDefinition dashBoardDefinition = getDashBoardDefinition(url);
		String filterName = urlMapper.GetFilterName(url);
		return dashBoardDefinition.getFilter(filterName);
		
	}

	@Override
	public ViewDefinition getViewDefinition(String url) {
		DashBoardDefinition dashBoardDefinition = getDashBoardDefinition(url);
		String viewName = urlMapper.GetViewName(url);
		return dashBoardDefinition.getView(viewName);
	}

	@Override
	public DashBoardDefinition getDashBoardDefinition(String url) {
		String dashBoardUrl = urlMapper.GetDashBoardDefinitionUrl(url);
		String dashBoardPath = urlMapper.GetDashBoardDefinitionPath(url);
		
		InputSource file = new InputSource(dashBoardPath);
		
		DashBoardDefinition dashBoardDefinition = dashBoardDefinitionSerializerSax.DeSerealize(file);
		dashBoardDefinition.setUrl(dashBoardUrl);
		return dashBoardDefinition;
	}
	
	@Override
	public List<MenuItemDefinition> getMenuDefinition(String url) {
		String menuPath = urlMapper.GetMenuDefinitionPath(url);
		
		InputSource file = new InputSource(menuPath);
		
		List<MenuItemDefinition> menuDefinition = menuDefinitionSerializerSax.DeSerealize(file);
		return menuDefinition;
	}

}
