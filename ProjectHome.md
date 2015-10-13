Firstly is a reporting dashboard front end based on html + javascript. It is builded on extJs framework (http://www.extjs.com/).

As a tool it revolves de following business problem:
http://www.kudewe.com/reports

You can use with your favourite server side technology using simple json services. Here you can see some sample videos:
http://www.kudewe.com/reports/demo-5-minutos

Or you can try on: http://reports.kudewe.com (user=demo, pass=demo).

As you can see the dashboard is a composition of views and filters. For loose coupling between them I use a publish/subcribe pattern using tibco page bus (http://developer.tibco.com/pagebus/default.jsp)

I choose an html + javascript based implementation for more people could use it. In my case I am using spring web as glue between client and server side.