package kudewe.reports.mvc.view;

import java.util.List;

import kudewe.reports.metadata.GenericDefinition;
import kudewe.reports.metadata.ViewDefinition;


public class LookDefinitionIterator {
	private GenericDefinition currentViewLook;
	private List<ViewDefinition> views;
	private int currentView = 0;

	public void setViews(List<ViewDefinition> views) {
		this.views = views;
	}
	
	public GenericDefinitionAdapter getNextLook() {
				
		if(currentView <= views.size()) {
			currentViewLook = views.get(currentView).getLookDefinition();
			currentView++;
		} else {
			currentViewLook = null;
		}
		
		return new GenericDefinitionAdapter(currentViewLook);
	}
	
	public LookDefinitionIterator clone() {
		return new LookDefinitionIterator();
	}
}
