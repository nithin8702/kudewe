package kudewe.reports.services.implementation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kudewe.reports.metadata.DashBoardDefinition;
import kudewe.reports.metadata.FilterDefinition;
import kudewe.reports.metadata.MenuItemDefinition;
import kudewe.reports.model.Filter;
import kudewe.reports.model.View;
import kudewe.reports.repository.FilterRepository;
import kudewe.reports.repository.LoadDB;
import kudewe.reports.repository.MetadataRepository;
import kudewe.reports.repository.ViewRepository;
import kudewe.reports.services.OlapService;

import com.google.gdata.util.ServiceException;

import mondrian.olap.Result;

/**
 * Service for perform olap operations
 * @author fer
 *
 */
public class OlapServiceImplementation implements OlapService<Result> {
	/**
	 * Filter repository
	 */
	private FilterRepository filterRepository;
	
	/**
	 * View repository
	 */
	private ViewRepository<Result> viewRepository;
	
	/**
	 * Metadata repository
	 */
	private MetadataRepository metadataRepository;
	
	/**
	 * Data base loader
	 */
	private LoadDB loadDB;
	
	/**
	 * Setter injection
	 * @param filterRepository Filter repository
	 */
	public void setFilterRepository(FilterRepository filterRepository) {
		this.filterRepository = filterRepository;
	}
	
	/**
	 * Setter injection
	 * @param filterRepository View repository
	 */
	public void setViewRepository(ViewRepository<Result> viewRepository) {
		this.viewRepository = viewRepository;
	}

	/**
	 * Setter injection
	 * @param metadataRepository Metadata repository
	 */
	public void setMetadataRepository(MetadataRepository metadataRepository) {
		this.metadataRepository = metadataRepository;
	}

	/**
	 * Setter injection
	 * @param loadDB DB loader
	 */
	public void setLoadDB(LoadDB loadDB) {
		this.loadDB = loadDB;
	}
	
	/**
	 * Return a filter
	 * @param filterUrl Filter's url
	 * @param filters Filters to apply
	 * @return Filter
	 */
	@Override
	public Filter getFilter(String filterUrl, Map<String, Filter> filters) {
		FilterDefinition filterDefinition = metadataRepository.getFilterDefinition(filterUrl);
		return filterRepository.getFilter(filterDefinition, filters);
	}
	
	/**
	 * Return a view
	 * @param viewUrl View's url
	 * @return View
	 */
	@Override
	public View<Result> getView(String viewUrl, Map<String, Filter> filters) {
		View<Result> view = new View<Result>();
		view.setDefinition(metadataRepository.getViewDefinition(viewUrl));
		view.setData(viewRepository.getView(view.getDefinition(), filters));
		return view;
	}
	
	/**
	 * Returns a dash board definition by a given url 
	 * @param dashBoardUrl
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ServiceException 
	 * @throws IOException 
	 */
	@Override
	public DashBoardDefinition getDashBoardDefinition(String dashBoardUrl) {
		DashBoardDefinition dashBoardDefinition = metadataRepository.getDashBoardDefinition(dashBoardUrl);
		//loadDB.loadDB(dashBoardDefinition.getConnection().getDataBaseAlias());
		return dashBoardDefinition;
	}
	
	/**
	 * Returns user's menu definition  
	 * @return
	 */
	@Override
	public List<MenuItemDefinition> getMenuDefinition() {
		return metadataRepository.getMenuDefinition("menu");
	}

}
