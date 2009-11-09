function buildMenu(menuDefinition, tabPanel) {    
	var tb = new Ext.Toolbar();
	
	Ext.each(
		menuDefinition.items, 
	    function(item, index, allItems) {
			var menu = new Ext.menu.Menu({
				text: item.name
			});

			tb.add({
				text: item.name,
	            menu: menu  // assign menu by instance
	        });
	        
			Ext.each(
				item.items, 
			    function(subItem, index, allItems) {
					var subMenu = menu.add({
				        text: subItem.name
				    });
					subMenu.dashBoardUrl = subItem.dashBoardUrl;
					subMenu.tabPanel = tabPanel;
					subMenu.parentText = menu.text;
				    subMenu.on('click', onItemClick);
				}
			);
		}
	);
    
	tb.add('->', {
		text: 'Logout',
		handler: function(){
			document.location.href='logout';
    	}
	});

 	return tb;   
}

var tabIndex = 0;
function onItemClick(item){
	var phId = 'dashBoard' + tabIndex;
	tabIndex++;
	
	var queryString = Ext.urlEncode({
		dashBoard: item.dashBoardUrl
	});
	
	Ext.DomHelper.append(document.body, {
	    id: phId,
	    cn: [{
	    	tag: 'iframe', 
	    	src: "dashBoard.html?" + queryString,
	    	width: '100%',
	    	height: '100%',
	    	frameborder: 0 
	    }]
	});
	
	item.tabPanel.add({
        title: item.parentText + ' / ' + item.text,
        contentEl: phId,
        closable: true
    }).show();
	
}
