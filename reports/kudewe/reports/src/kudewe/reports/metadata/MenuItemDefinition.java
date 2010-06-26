package kudewe.reports.metadata;

import java.util.ArrayList;
import java.util.List;

public class MenuItemDefinition {
	private String name;
	private String dashBoardUrl;
	private List<MenuItemDefinition> items = new ArrayList<MenuItemDefinition>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDashBoardUrl() {
		return dashBoardUrl;
	}
	public void setDashBoardUrl(String dashBoardUrl) {
		this.dashBoardUrl = dashBoardUrl;
	}
	public List<MenuItemDefinition> getItems() {
		return new ArrayList<MenuItemDefinition>(items);
	}
	
	public void addItem(MenuItemDefinition menuItemDefinition) {
		items.add(menuItemDefinition);
	}
}
