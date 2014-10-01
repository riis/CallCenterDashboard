<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        
        <!-- 3rd party / vendor Javascript Files -->
        <script src="<%=request.getContextPath()%>/resources/bower_components/angular/angular.js"></script>
        <script src="<%=request.getContextPath()%>/resources/bower_components/angular-route/angular-route.js"></script>
        <script src="<%=request.getContextPath()%>/resources/bower_components/angular-resource/angular-resource.js"></script>
        <script src="<%=request.getContextPath()%>/resources/bower_components/angular-ui-router/release/angular-ui-router.js"></script>
        <script src="<%=request.getContextPath()%>/resources/bower_components/jquery/dist/jquery.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
        
        <!-- Functions -->
        <script src="<%=request.getContextPath()%>/resources/common/functions.js"></script>
        
        <!-- App Javascript Files -->
        <script src="<%=request.getContextPath()%>/resources/app/app.js"></script>
        <script src="<%=request.getContextPath()%>/resources/app/dashboard/dashboard.js"></script>

        <!-- Services -->
        <script src="<%=request.getContextPath()%>/resources/common/service/agents/agentsService.js"></script>
        
        <!-- Factories -->
        <script src="<%=request.getContextPath()%>/resources/common/factory/agents/agentsFactory.js"></script>
        
        <!-- Filters -->
        <script src="<%=request.getContextPath()%>/resources/common/filter/partition/partitionFilter.js"></script>

        
        <!-- CSS Files -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/bower_components/bootstrap/dist/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/bower_components/bootstrap/dist/css/bootstrap-theme.css" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/bower_components/components-font-awesome/css/font-awesome.min.css" />
        
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/style.css" />
        
    </head>
    <body ng-controller="AppCtrl">
    
		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Call Center Activity</a>
				</div>

				<div class="pull-right date-time">
					
					<div class="date-container">
						<div class="day">Monday</div>
						<div class="date">10 <span class="month">Feb</span></div>
					</div>
					
					<div class="time-container">
						<div class="am-pm">PM</div>
						<div class="time">03:25</div>
					</div>
				</div>
			</div>
		</div>
	    
		<div class="container-fluid">
				<div ui-view="main" class="view-frame"></div>
			</div>
		</div>
    	
    </body>
</html>
