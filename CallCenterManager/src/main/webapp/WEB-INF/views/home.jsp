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
        <script src="<%=request.getContextPath()%>/resources/bower_components/angular-pusher/angular-pusher.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/bower_components/angular-ui-router/release/angular-ui-router.js"></script>
        <script src="<%=request.getContextPath()%>/resources/bower_components/angular-google-chart/ng-google-chart.js"></script>
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
        <script src="<%=request.getContextPath()%>/resources/common/filter/status/statusFilter.js"></script>

        <!-- Directives -->
        <script src="<%=request.getContextPath()%>/resources/common/directive/highlighter/highlighterDirective.js"></script>

        
        <!-- CSS Files -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/bower_components/bootstrap/dist/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/bower_components/bootstrap/dist/css/bootstrap-theme.css" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/bower_components/components-font-awesome/css/font-awesome.min.css" />
        
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/style.css" />
        
    </head>
    <body ng-controller="AppCtrl">
    
		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container-fluid drop-shadow">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Call Center Activity</a>
				</div>
				<div class="pull-right date-time">
					<div class="date-container">
						<div class="day" ng-bind="dateObj | date:'EEEE'"></div>
						<div><span class="date" ng-bind="dateObj | date:'dd'"></span> <span class="month" ng-bind="dateObj | date:'MMM'"></span></div>
					</div>
					<div class="time-container">
						<div class="am-pm" ng-bind="dateObj | date:'a'"></div>
						<div class="time" ng-bind="dateObj | date:'h:mm'"></div>
					</div>
				</div>
                <div class="time-label pull-right">Last Updated</div>
			</div>
		</div>
	    
		<div class="container-fluid">
				<div ui-view="main" class="view-frame"></div>
			</div>
		</div>
    	
    </body>
</html>
