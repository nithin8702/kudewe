Ext.chart.Chart.CHART_URL = 'lib/ext-3.0.0/resources/charts.swf';

//Build dashboard's filters
function buildFilters(dashBoardDefinition) {
    
	// Build filters
	var filters = new Array();
	Ext.each(
		dashBoardDefinition.filters, 
	    function(item, index, allItems) {
			filters.push(buildComboFilter(item))
		}
	);
	
	return filters;
}

// Build dash board
function buildDashBoard(dashBoardDefinition) {
    return buildLayoutPortal(dashBoardDefinition.views);
}

function buildLayoutPortal(viewDefinitions) {
	var portal =  {
		xtype:'portal',
        region:'center',
        margins:'0 0 0 0',
        items:[{
        	columnWidth:.5,
            style:'padding:5px 5px 5px 5px',
            items:[]
        },{
        	columnWidth:.5,
            style:'padding:5px 5px 5px 5px',
            items:[]
        }]
	}

	Ext.each(
			viewDefinitions, 
		    function(viewDefinition, index, allItems) {
				var column;
				if (index % 2 == 0) {
					portalColumn = portal.items[0];
				} else {
					portalColumn = portal.items[1];
				}
				portalColumn.items.push({
					title: viewDefinition.look.title,
					layout:'fit',
	                items: buildView(viewDefinition)
				});
			}
		);
	
	portal.items[0].items.push({
		title: 'Debug',
		layout:'fit',
        items: buildViewDebug()
	});
	
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
