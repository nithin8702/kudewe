<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>

<html>
  <head>
    <title>Kudewe Reports</title>
    <!-- ** CSS ** -->
    <!-- base library -->
    <link rel="stylesheet" type="text/css" href="lib/ext-3.0.0/resources/css/ext-all.css" />

    <!-- ** Javascript ** -->
    <!-- ExtJS library: base/adapter -->
 	<script type="text/javascript" src="lib/ext-3.0.0/adapter/ext/ext-base-debug.js"></script>
	
 	<!-- ExtJS library: all widgets -->
    <script type="text/javascript" src="lib/ext-3.0.0/ext-all-debug.js"></script>
	
	<link rel="stylesheet" type="text/css" href="lib/ext-3.0.0/examples/form/forms.css"/>

    <!-- Common Styles for the examples -->
    <link rel="stylesheet" type="text/css" href="lib/ext-3.0.0/examples/shared/examples.css"/>
	
	
	<script type="text/javascript">
    Ext.onReady(function(){
    	Ext.QuickTips.init();
		
        // turn on validation errors beside the field globally
        Ext.form.Field.prototype.msgTarget = 'side';

        var simple = new Ext.FormPanel({
            labelWidth: 75, // label settings here cascade unless overridden
            //method: 'POST',
            //url: 'login_check',
            //standardSubmit: true,
            bodyStyle:'padding:5px 5px 0',
            frame:true,
            title: 'Acceso',
            width: 350,
            defaults: {width: 230},
            defaultType: 'textfield',

            items: [{
                    fieldLabel: 'Usuario',
                    name: 'username',
                    allowBlank: false,
                    value: Ext.get('j_username').getValue()
                },{
                	inputType: 'password',
                    fieldLabel: 'Password',
                    name: 'password',
                    allowBlank: false
                },{
                	xtype: 'checkbox',
                    fieldLabel: 'Recordarme',
                    name: 'rememberme'
                }
            ],

            buttons: [{
                text: 'Ingresar',
                handler: function(){
	                var fp = this.ownerCt.ownerCt,
	                    form = fp.getForm();
	                if (form.isValid()) {
	                	document.getElementById('j_username').value = form.items.get(0).getValue();
	                	document.getElementById('j_password').value = form.items.get(1).getValue();
	                	document.getElementById('_spring_security_remember_me').value = form.items.get(2).getValue();
		                document.getElementById('frmMain').submit();
	                }
	            }
            }]
        });

        simple.render('divForm');
        Ext.get('divMessage').removeClass('x-hide-display');
    });
	</script>
  </head>

  <body>
  	<div id="divForm"></div>
  	<form id="frmMain" method="post" action="login_check">
    	<input id='j_username' type='hidden' name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/>
		<input id='j_password' type='hidden' name='j_password'>
		<input id="_spring_security_remember_me" type="hidden" name="_spring_security_remember_me">
    </form>
    <div id="divMessage" class="x-hide-display">
	    <c:if test="${not empty param.login_error}">
	      <font color="red">
	      	Su intento de acceso no fue exitoso, por favor intente nuevamente.<br/><br/>
	        Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
	      </font>
	    </c:if>
    </div>
  </body>
</html>