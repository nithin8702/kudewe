function buildView(viewDefinition) {
	if (viewDefinition.look.lookType == 'ext.grid') {
		return buildViewGrid(viewDefinition);
	} else if (viewDefinition.look.lookType == 'ext.graph') {
		return buildViewGraph(viewDefinition);
	}
}

function buildViewStore(viewDefinition) {
	return new Ext.data.JsonStore({
        root: 'data',
        fields: viewDefinition.look.fields,
        proxy : new Ext.data.HttpProxy({
            method: 'GET',
            url: 'services/' + viewDefinition.url + '.json'

       })
    });
}

function buildViewGrid(gridDefinition) {
	// create the Data Store
    var storeView = buildViewStore(gridDefinition);
	
	// make all columns sortable
    Ext.each(
    	gridDefinition.look.columns, 
	    function(item, index, allItems) {
    		item.sortable = true;
		}
	);
	
	// create grid
    var viewGrid = new Ext.grid.GridPanel({
        store: storeView,
        columns: gridDefinition.look.columns,
        stripeRows: true,
        height:gridDefinition.look.height
    });
	
	// trigger the data store load
    storeView.load({params:{start:0, limit:25}});
    
	// Subscribe store view to bus
    subscribeStoreViewToBus(storeView, gridDefinition)
    
	return viewGrid;
}

function subscribeStoreViewToBus(store, viewDefinition) {
	// Create a scope object
	var myScope = {};
	
	// Create a subscriberData with storeReport
	var mySubscriberData = {
		store: store,
		dependencies: viewDefinition.dependencies
	};
	
	// Subscribe to a subject.
	var mySubscription = window.PageBus.subscribe(
		"dash.filter.onSelect",
		myScope,
		function(subject, message, subscriberData) {
			// If prompt changed is in dependencies
			if (subscriberData.dependencies.indexOf(message.filterName) >= 0) {
				var paramsFilter = {}
	
				// Query to bus for get selected filters
				window.PageBus.query(
					'dash.filter.*', 
					myScope, 
					function(subject, value, data) {
						if (subject != 'com.tibco.pagebus.query.done') {
							// Add filter to params
							eval('paramsFilter.' + value.filterName + '="' + value.filterValue + '"');
							
							// Get next result
							return true;
						} else {
							// Reload data
							subscriberData.store.reload({
								params: paramsFilter
							});
						}
					},
					null,
					null
				);
			}
		},
		mySubscriberData);
}

function buildViewGraph(graphDefinition) {
	
	// create the Data Store
    var storeView = buildViewStore(graphDefinition);
	
    var view = new Ext.Panel({
        width: graphDefinition.look.width - 20,
        height: graphDefinition.look.height - 20,
        layout:'fit',
        items: {
            xtype: graphDefinition.look.xtype,
            store: storeView,
            xField: graphDefinition.look.xField,
            series: graphDefinition.look.series
        }
    });

	// trigger the data store load
    storeView.load({params:{start:0, limit:25}});

 	// Subscribe store view to bus
    subscribeStoreViewToBus(storeView, graphDefinition)
    
    return view;
}

function buildViewDebug() {
	// Reporte debug
	var viewDebug = new Ext.Panel({
		width: '100%',
		contentEl: 'panelDebugPagebus',
		autoScroll: true
	});

	// Suscribo reporte al bus
	// Create a scope object
	var myScope = {};
	
	// Create a subscriberData.
	// This could be an object or simply a string, or null.
	var mySubscriberData = { phId: 'panelDebugPagebus', ph: viewDebug };
	
	// Subscribe to a subject.
	var mySubscription = window.PageBus.subscribe(
		"dash.filter.onSelect",
		myScope,
		function(subject, message, subscriberData) {
			document.getElementById(subscriberData.phId).innerHTML = 
				subject + ' ' +
				message.filterName + ' ' +
				message.filterValue+ '<br/>';

			window.PageBus.query(
				'dash.filter.*', 
				myScope, 
				function(subject, value, data) {
					if (subject != 'com.tibco.pagebus.query.done') {
						document.getElementById(subscriberData.phId).innerHTML += 
							value.filterName + ' ' +
							value.filterValue + '<br/>';
						return true;
					}
				},
				null,
				null
			);
		},
		mySubscriberData);

	return viewDebug;
}
