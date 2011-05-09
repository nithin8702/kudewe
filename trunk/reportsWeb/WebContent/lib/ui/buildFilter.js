function buildComboFilter(filterDefinition) {
	// Create store for filter
	var storeFilter = buildFilterStore(filterDefinition);
	
	// Make combo filter
	var comboFilter = new Ext.form.ComboBox({
		store: storeFilter,
        valueField: 'id',
        displayField: 'name',
	    forceSelection: true,
	    loadingText: 'Loading...',
	    triggerAction: 'all',
	    mode: 'remote'
	});

	comboFilter.on('beforequery', function(queryEvent , store) {
		if (queryEvent.combo.store.getCount() == 0) {
			queryEvent.combo.store.load();
		}
	});
	
	// Set on select event handler
	comboFilter.on('select', function() {
		// Create the message
		var message = {
			filterName: filterDefinition.name,
			filterValue: comboFilter.getValue(),
			filterText: comboFilter.store.getById(comboFilter.getValue()).data.name
		};
		
		// Save filter on page bus
		window.PageBus.store('dash.filter.' + message.filterName, message)
		
		// Remove dependants on page bus
		Ext.each(
			filterDefinition.dependants, 
		    function(item, index, allItems) {
				window.PageBus.store('dash.filter.' + item, null)
			}
		);

		window.PageBus.store('dash.filter.' + message.filterName, message)
		
		// Publish the message using PageBus
		window.PageBus.publish('dash.filter.onSelect', message);
	});
	
	// Subscribe store view to bus
    subscribeStoreFilterToBus(storeFilter, filterDefinition, comboFilter)
    
    //storeFilter.load({params:{start:0}});
    
	return {
		title: filterDefinition.label,
		border: false,
		collapsible: true,
		items: [
			comboFilter
		]
	};
}

function buildFilterStore(filterDefinition) {
	return new Ext.data.Store({
		fields: ['id', 'name'],
		proxy: {
	        type: 'ajax',
	        url : 'olap/' + filterDefinition.url + '.json',
	        //noCache: false,
	        pageParam: null,
	        startParam: null,
	        limitParam: null,
	        reader: {
	            type: 'json',
	            root: 'data'
	        }
	    },
	    autoLoad: false
	});
}

function subscribeStoreFilterToBus(store, filterDefinition, comboFilter) {
	// Create a scope object
	var myScope = {};
	
	// Create a subscriberData with storeReport
	var mySubscriberData = {
		store: store,
		dependencies: filterDefinition.dependencies,
		filter: comboFilter
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
							subscriberData.filter.clearValue();
						}
					},
					null,
					null
				);
			}
		},
		mySubscriberData);
}