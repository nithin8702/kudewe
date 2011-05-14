function buildView(viewDefinition) {
	subscribeTitleToBus(viewDefinition);
	if (viewDefinition.look.lookType == 'ext.grid') {
		return buildViewGrid(viewDefinition);
	} else if (viewDefinition.look.lookType == 'ext.graph') {
		return buildViewGraph(viewDefinition);
	}
}

function buildViewStore(viewDefinition) {
	return new Ext.data.Store({
		fields: viewDefinition.look.fields,
		proxy: {
	        type: 'ajax',
	        url : 'olap/' + viewDefinition.url + '.json',
	        pageParam: null,
	        startParam: null,
	        limitParam: null,
	        reader: {
	            type: 'json',
	            root: 'data'
	        },
	        encodeFilters: function(filters) {
	            var length   = filters.length,
	                filterStrs = [],
	                filter, i;

	            for (i = 0; i < length; i++) {
	            	filter = filters[i];

	            	filterStrs[i] = filter.property + '=' + filter.value
	            }
	            return filterStrs.join("&");
	        }
	    },
	    autoLoad: false,
	    remoteFilter: true
	});
}

var loadStoreDelay = 1000;
var loadStoreTime = loadStoreDelay;
function loadStore(store) {
	store.load({params:{}});
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
        //stripeRows: true,
        height:gridDefinition.look.height
    });
	
	// trigger the data store load
    loadStore(storeView);
    
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
				var filters = [];
	
				// Query to bus for get selected filters
				window.PageBus.query(
					'dash.filter.*', 
					myScope, 
					function(subject, value, data) {
						if (subject != 'com.tibco.pagebus.query.done') {
							// Add filter to params
							filters.push({
					            property: value.filterName,
					            value: value.filterValue
					        })
							// Get next result
							return true;
						} else {
							// Reload data
							subscriberData.store.filter(filters);
						}
					},
					null,
					null
				);
			}
		},
		mySubscriberData);
}

function subscribeTitleToBus(viewDefinition) {
	// Create a scope object
	var myScope = {};
	
	// Create a subscriberData with storeReport
	var mySubscriberData = {
		viewDefinition: viewDefinition
	};
	
	// Subscribe to a subject.
	var mySubscription = window.PageBus.subscribe(
		"dash.filter.onSelect",
		myScope,
		function(subject, message, subscriberData) {
			var filters = '';

			// Query to bus for get selected filters
			window.PageBus.query(
				'dash.filter.*', 
				myScope, 
				function(subject, value, data) {
					if (subject != 'com.tibco.pagebus.query.done') {
						// If filter is in view's dependencies
						if (subscriberData.viewDefinition.dependencies.indexOf(value.filterName) >= 0
							&& value.filterValue.search(/.\[All\]$/) < 0) {
							if (filters) {
								filters = filters + ', ';
							}
							filters = filters + value.filterText;
						}
						// Get next result
						return true;
					} else {
						// Enclose title
						if (filters) {
							filters = '(' + filters + ')';
						}
						// Reload title
						var container = Ext.getCmp('container' + subscriberData.viewDefinition.name);
						container.setTitle(subscriberData.viewDefinition.look.title + ' ' + filters);
					}
				},
				null,
				null
			);
		},
		mySubscriberData);
}

function buildViewGraph(graphDefinition) {
	
	// create the Data Store
    var storeView = buildViewStore(graphDefinition);
	
    // set tip
    Ext.each(
    	graphDefinition.look.series, 
	    function(serie, index, allItems) {
    		serie.tips = {
				trackMouse: true,
		        width: 200,
		        height: 28,
		        renderer: function(storeItem, item) {
					this.setTitle(storeItem.get(serie.xField) + ': ' + storeItem.get(serie.yField));
		        }
    		};
		}
	);
    
    var view = new Ext.Panel({
        width: graphDefinition.look.width - 20,
        height: graphDefinition.look.height - 20,
        layout:'fit',
        items: {
            xtype: "chart",
            store: storeView,
            animate: true,
            //xField: graphDefinition.look.xField,
            theme: graphDefinition.look.theme,
            legend: graphDefinition.look.legend,
            axes: graphDefinition.look.axes,
            series: graphDefinition.look.series
    		//yAxis: new Ext.chart.NumericAxis({
                //labelRenderer : Ext.util.Format.numberRenderer('0.000/i')
            //})
        }
    });
    
	// trigger the data store load
    loadStore(storeView);
    
 	// Subscribe store view to bus
    subscribeStoreViewToBus(storeView, graphDefinition)
    
    return view;
}

function buildViewDebug() {
	// Reporte debug
	var viewDebug = new Ext.Panel({
		width: '100%',
		contentEl: 'panelDebugPagebus',
		autoScroll: true,
		height: 100
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

Ext.util.Format.intNumber = function (value) {
	return Ext.util.Format.number(value, '0.000/i');
}
