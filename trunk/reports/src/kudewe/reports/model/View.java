package kudewe.reports.model;

import kudewe.reports.metadata.ViewDefinition;

public class View<Result> {
	private Result data;
	private ViewDefinition definition;
	
	public Result getData() {
		return data;
	}
	public void setData(Result data) {
		this.data = data;
	}
	
	public ViewDefinition getDefinition() {
		return definition;
	}
	public void setDefinition(ViewDefinition definition) {
		this.definition = definition;
	}
}
