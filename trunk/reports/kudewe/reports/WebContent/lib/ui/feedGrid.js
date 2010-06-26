/*!
 * Ext JS Library 3.0.0
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
FeedGridRse = function(config) {
    Ext.apply(this, config);

    this.store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'GET'
        }),

        reader: new Ext.data.XmlReader(
	            {record: 'item'},
	            ['title', 'author', {name:'pubDate', type:'date'}, 'link', 'description', 'content']
	        )
    });
    this.store.setDefaultSort('pubDate', "DESC");

    this.columns = [{
        id: 'title',
        header: "Titulo",
        dataIndex: 'title',
        sortable:true,
        width: 420,
        renderer: this.formatTitle
      },{
        header: "Author",
        dataIndex: 'author',
        width: 100,
        hidden: true,
        sortable:true
      },{
        id: 'last',
        header: "Fecha",
        dataIndex: 'pubDate',
        width: 150,
        renderer:  this.formatDate,
        sortable:true
    }];

    FeedGridRse.superclass.constructor.call(this, {
        //region: 'center',
        id: 'topic-grid',
        loadMask: {msg:'Loading Feed...'},

        sm: new Ext.grid.RowSelectionModel({
            singleSelect:true
        }),

        viewConfig: {
            forceFit:true,
            enableRowBody:true,
            showPreview:true,
            getRowClass : this.applyRowClass
        }
    });

    this.on('rowcontextmenu', this.onContextClick, this);
};

Ext.extend(FeedGridRse, Ext.grid.GridPanel, {

    onContextClick : function(grid, index, e){
        if(!this.menu){ // create context menu on first right click
            this.menu = new Ext.menu.Menu({
                id:'grid-ctx',
                items: [{
                    iconCls: 'new-win',
                    text: 'Go to Post',
                    scope:this,
                    handler: function(){
                        window.open(this.ctxRecord.data.link);
                    }
                },'-',{
                    iconCls: 'refresh-icon',
                    text:'Refresh',
                    scope:this,
                    handler: function(){
                        this.ctxRow = null;
                        this.store.reload();
                    }
                }]
            });
            this.menu.on('hide', this.onContextHide, this);
        }
        e.stopEvent();
        if(this.ctxRow){
            Ext.fly(this.ctxRow).removeClass('x-node-ctx');
            this.ctxRow = null;
        }
        this.ctxRow = this.view.getRow(index);
        this.ctxRecord = this.store.getAt(index);
        Ext.fly(this.ctxRow).addClass('x-node-ctx');
        this.menu.showAt(e.getXY());
    },

    onContextHide : function(){
        if(this.ctxRow){
            Ext.fly(this.ctxRow).removeClass('x-node-ctx');
            this.ctxRow = null;
        }
    },

    loadFeed : function(url) {
    	this.store.proxy = new Ext.data.HttpProxy({
    		url: url,
            method: 'GET'
        });
        this.store.load();
    },

    togglePreview : function(show){
        this.view.showPreview = show;
        this.view.refresh();
    },

    // within this function "this" is actually the GridView
    applyRowClass: function(record, rowIndex, p, ds) {
        if (this.showPreview) {
            var xf = Ext.util.Format;
            p.body = '<p>' + xf.ellipsis(xf.stripTags(record.data.description), 200) + '</p>';
            return 'x-grid3-row-expanded';
        }
        return 'x-grid3-row-collapsed';
    },

    formatDate : function(date) {
        if (!date) {
            return '';
        }
        var now = new Date();
        var d = now.clearTime(true);
        var notime = date.clearTime(true).getTime();
        if (notime == d.getTime()) {
            return 'Today ' + date.dateFormat('g:i a');
        }
        d = d.add('d', -6);
        if (d.getTime() <= notime) {
            return date.dateFormat('D g:i a');
        }
        return date.dateFormat('n/j g:i a');
    },

    formatTitle: function(value, p, record) {
        return String.format(
                '<div class="topic"><b>{0}</b><span class="author">{1}</span></div>',
                value, record.data.author, record.id, record.data.forumid
                );
    }
});

FeedGridTwitter = function(config) {
    Ext.apply(this, config);

    this.store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'GET'
        }),

        reader: new Ext.data.XmlReader(
	            {record: 'status'},
	            ['text']
	        )
    });

    this.columns = [{
        id: 'text',
        header: "Twitt",
        dataIndex: 'text',
        renderer: this.formatTitle,
        width: 580,
        sortable: true
      }];

    FeedGridTwitter.superclass.constructor.call(this, {
        //region: 'center',
        id: 'topic-grid',
        loadMask: {msg:'Loading Feed...'},

        sm: new Ext.grid.RowSelectionModel({
            singleSelect:true
        }),

        viewConfig: {
            forceFit:true,
            enableRowBody:true,
            showPreview:true
        }
    });

    this.on('rowcontextmenu', this.onContextClick, this);
};

Ext.extend(FeedGridTwitter, Ext.grid.GridPanel, {

    onContextClick : function(grid, index, e){
        if(!this.menu){ // create context menu on first right click
            this.menu = new Ext.menu.Menu({
                id:'grid-ctx',
                items: [{
                    iconCls: 'refresh-icon',
                    text:'Refresh',
                    scope:this,
                    handler: function(){
                        this.ctxRow = null;
                        this.store.reload();
                    }
                }]
            });
            this.menu.on('hide', this.onContextHide, this);
        }
        e.stopEvent();
        if(this.ctxRow){
            Ext.fly(this.ctxRow).removeClass('x-node-ctx');
            this.ctxRow = null;
        }
        this.ctxRow = this.view.getRow(index);
        this.ctxRecord = this.store.getAt(index);
        Ext.fly(this.ctxRow).addClass('x-node-ctx');
        this.menu.showAt(e.getXY());
    },

    onContextHide : function(){
        if(this.ctxRow){
            Ext.fly(this.ctxRow).removeClass('x-node-ctx');
            this.ctxRow = null;
        }
    },

    loadFeed : function(url) {
    	this.store.proxy = new Ext.data.HttpProxy({
    		url: url,
            method: 'GET'
        });
        this.store.load();
    },

    togglePreview : function(show){
        this.view.showPreview = show;
        this.view.refresh();
    },

    formatTitle: function(value, p, record) {
    	var xf = Ext.util.Format;
        var body = '<p style="white-space:normal;">' + xf.ellipsis(xf.stripTags(record.data.text), 200) + '</p>';
        return body;
    	return String.format(
                '<div class="topic"><p>hola</p></div>',
                value
                );
    }
});
