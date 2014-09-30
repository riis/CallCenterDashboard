<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        
        <!-- Javascript Files -->
        <script src="<%=request.getContextPath()%>/resources/bower_components/angular/angular.js"></script>
        <script src="<%=request.getContextPath()%>/resources/bower_components/angular-route/angular-route.js"></script>
        <script src="<%=request.getContextPath()%>/resources/bower_components/angular-resource/angular-resource.js"></script>
        <script src="<%=request.getContextPath()%>/resources/bower_components/angular-ui-router/release/angular-ui-router.js"></script>
        
        <script src="<%=request.getContextPath()%>/resources/bower_components/jquery/dist/jquery.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
        
        <script src="<%=request.getContextPath()%>/resources/js/app.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/controllers/home.js"></script>
        
        
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
	          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
	            <span class="sr-only">Toggle navigation</span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </button>
	          <a class="navbar-brand" href="#">RoadRunner Call Center</a>
	        </div>
	      </div>
	    </div>
	    
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3 col-md-2 sidebar">
					<ul class="nav nav-sidebar">
						<li class="active"><a href="#">Dashboard</a></li>
					</ul>
				</div>
				

				<div ui-view="main" class="view-frame"></div>
			</div>
		</div>
    	
    </body>
</html>
