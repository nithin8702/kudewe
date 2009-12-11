package kudewe.reports.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kudewe.reports.metadata.DashBoardDefinition;
import kudewe.reports.metadata.GenericDefinition;
import kudewe.reports.metadata.ViewDefinition;
import kudewe.reports.mvc.view.LookDefinitionIterator;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


public class OlapDashBoardDefinitionTestController extends AbstractController  {
	private LookDefinitionIterator lookDefinitionIteratorSingleton;

	public void setLookDefinitionIterator(LookDefinitionIterator lookDefinitionIterator) {
		this.lookDefinitionIteratorSingleton = lookDefinitionIterator;
	}
	
	/**
	 * Execute a service that returns the dash board definition and map with a page that render in corresponding format (json)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DashBoardDefinition dashBoardDefinition = new DashBoardDefinition();
		ViewDefinition view = new ViewDefinition();
		view.setName("view1");
		dashBoardDefinition.addView(view);
		
		GenericDefinition look = new GenericDefinition();
		view.setLookDefinition(look);
		
		// Simple properties
		look.addProperty(new GenericDefinition("title", "Productos por Mes"));
		look.addProperty(new GenericDefinition("width", 400));
		look.addProperty(new GenericDefinition("height", 200));
		
		// Array string property
		List<String> fields = new ArrayList<String>();
		fields.add("Product");
		fields.add("SalePrice");
		fields.add("Quantity");
		
		look.getProperties().add(new GenericDefinition("fields", fields));
		
		// Array definition property
		List<GenericDefinition> columns = new ArrayList<GenericDefinition>();
		
		GenericDefinition column1 = new GenericDefinition();
		column1.addProperty(new GenericDefinition("header", "Product"));
		column1.addProperty(new GenericDefinition("dataIndex", "Product"));
		columns.add(column1);
		
		GenericDefinition column2 = new GenericDefinition();
		column2.addProperty(new GenericDefinition("header", "Precio Venta $"));
		column2.addProperty(new GenericDefinition("dataIndex", "SalePrice"));
		columns.add(column2);
		
		GenericDefinition column3 = new GenericDefinition();
		column3.addProperty(new GenericDefinition("header", "Cantidad"));
		column3.addProperty(new GenericDefinition("dataIndex", "Quantity"));
		columns.add(column3);
		
		look.addProperty(new GenericDefinition("columns", columns));
		
		// Clone olap look iterator because concurrent access
		LookDefinitionIterator lookDefinitionIterator = lookDefinitionIteratorSingleton.clone();
		lookDefinitionIterator.setViews(dashBoardDefinition.getViews());
		
		// Map to view
		ModelAndView modelAndView = new ModelAndView("/jsp/dashBoardDefinition2json.jsp", "dashBoardDefinition", dashBoardDefinition);
		modelAndView.addObject("lookDefinitionIterator", lookDefinitionIterator);
		return modelAndView;
	}

}
