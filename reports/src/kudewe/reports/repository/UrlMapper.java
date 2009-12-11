package kudewe.reports.repository;

//TOOD document
public interface UrlMapper {
	String GetDashBoardDefinitionPath(String url);
	String GetDashBoardDefinitionUrl(String url);
	String GetFilterName(String url);
	String GetViewName(String url);
	String GetMenuDefinitionPath(String url);
}
