Ext.chart.Chart.CHART_URL = 'lib/ext-3.0.0/resources/charts.swf';

//Build dashboard's filters
function buildFilters(dashBoardDefinition) {
    
	// Build filters
	var filters = new Array();
	Ext.each(
		dashBoardDefinition.filters, 
	    function(item, index, allItems) {
			if (item.label) {
				filters.push(buildComboFilter(item));
			}
		}
	);
	
	return filters;
}

// Build dash board
function buildDashBoard(dashBoardDefinition) {
	setDefaultValues(dashBoardDefinition);
	return buildLayoutPortal(dashBoardDefinition);
}

function setDefaultValues(dashBoardDefinition) {
	if (!dashBoardDefinition.look) {
		dashBoardDefinition.look = {};
	}
	if (!dashBoardDefinition.look.cols) {
		dashBoardDefinition.look.cols = 2;
	}
}

function buildLayoutPortal(dashBoardDefinition) {
	var viewDefinitions = dashBoardDefinition.views;
	var portal =  {
		xtype:'portal',
        region:'center',
        margins:'0 0 0 0',
        items:[]
	};
	
	for (i = 0; i < dashBoardDefinition.look.cols; i++) {
		portal.items.push({
        	columnWidth: 1 / dashBoardDefinition.look.cols,
            style:'padding:5px 5px 5px 5px',
            items:[]
        });
	}

	Ext.each(
		viewDefinitions, 
	    function(viewDefinition, index, allItems) {
			var portalColumn = portal.items[index % dashBoardDefinition.look.cols];
			portalColumn.items.push({
				id: "container" + viewDefinition.name,
				title: viewDefinition.look.title,
				layout:'fit',
                items: buildView(viewDefinition)
			});
		}
	);
	/*
	portal.items[0].items.push({
		title: 'Debug',
		layout:'fit',
        items: buildViewDebug()
	});
	*/
	
	return portal;
}

function buildLayoutTable(viewDefinitions) {
	var layoutTable =  {
		region:'center',
		layout:'table',
		layoutConfig: {
			columns: 2
		},
        // applied to child components
        defaults: {frame:true, width:300, height: 200},
		items:[]
	}

	Ext.each(
			viewDefinitions, 
		    function(viewDefinition, index, allItems) {
				layoutTable.items.push({
					title: viewDefinition.look.title,
					height: viewDefinition.look.height,
		            width: viewDefinition.look.width,
	                items: buildView(viewDefinition)
				});
			}
		);
	
	layoutTable.items.push({
		title: 'Debug',
		layout:'fit',
        items: buildViewDebug()
	});
	
	return layoutTable;
}
