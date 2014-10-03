'use strict';

angular.module('roadrunner.dashboard', [
    'ui.router'
])

/**
 * Each section or module of the site can also have its own routes. AngularJS
 * will handle ensuring they are all available at run-time, but splitting it
 * this way makes each module more "self-contained".
 */
.config(function config($stateProvider) {
    $stateProvider.state('dashboard', {
        url: '/dashboard',
        views: {
            "main": {
                controller: 'DashboardCtrl',
                templateUrl: 'resources/app/dashboard/dashboard.tpl.html'
            }
        },
        data: {
            pageTitle: 'Here is the page Title'
        }
    });
})

/**
 * And of course we define a controller for our route.
 */
.controller('DashboardCtrl',
	function DashboardController($rootScope, $scope, $location, $routeParams, agentsService, Pusher) {
		$scope.pageTitle = 'Dashboard';
		
		// Start Loader...
		startLoader('#agents .panel-body');
		
		//---------------------------------------------------------------------------
		// Get the agents from the Web Service
		//---------------------------------------------------------------------------
		var agents = agentsService.getAgents(function (response) {
			// Success...
			$scope.agents = response;

			endLoader('#agents .panel-body');
		}, function (error) {
			// Error...
			endLoader('#agents .panel-body');
			$scope.agentsError = true;
		});
		
		
		$scope.testItems = [{id: 4, name: 'test'},{id: 5, name: 'test5'},{id: 6, name: 'test6'}];
		console.dir($scope.testItems);
		
		Pusher.subscribe('channel-two', 'agentEvent', function (item) {
			console.log('ragentEvent...');
			console.dir(item);
			
			$('body').append('<p>Received a agentEvent...</p>');
			// an item was updated. find it in our list and update it.
			for (var i = 0; i < $scope.testItems.length; i++) {
				if ($scope.testItems[i].id === item.id) {
					$scope.testItems[i] = item;
					break;
				}
			}
			
			console.dir($scope.testItems);
		});
		
		
		// Start Loader...
		startLoader('#agentSummary .panel-body');
		//---------------------------------------------------------------------------
		// Agent Summary
		//---------------------------------------------------------------------------
		var agents = agentsService.getAgentSummary(function (response) {
			// Success...
			$scope.callCenterAgentSummary = response;

			endLoader('#agentSummary .panel-body');
		}, function (error) {
			// Error...
			endLoader('#agentSummary .panel-body');
			$scope.agentSummaryError = true;
		});
		
		

		//---------------------------------------------------------------------------
		// Calls In Queue
		//---------------------------------------------------------------------------
	    $scope.chartObject = {};
	
	    $scope.chartObject.data = {"cols": [
	        {id: "t", label: "Call Center", type: "string"},
	        {id: "s", label: "Calls In Queue", type: "number"}
	    ], "rows": [
	        {c: [
	            {v: "Call Center 1"},
	            {v: 3},
	        ]},
	        {c: [
	            {v: "Call Center 2"},
	            {v: 31}
	        ]},
	        {c: [
	            {v: "Call Center 3"},
	            {v: 1},
	        ]},
	        {c: [
	            {v: "Call Center 4"},
	            {v: 2},
	        ]}
	    ]};
	
	
	    // $routeParams.chartType == BarChart or PieChart or ColumnChart...
	    $scope.chartObject.type = 'BarChart';
	    $scope.chartObject.options = {};

		
		
		$scope.testItems = [{id: 4, name: 'test'},{id: 5, name: 'test5'},{id: 6, name: 'test6'}];
		console.dir($scope.testItems);
		
		Pusher.subscribe('channel-two', 'callCenterEvent', function (item) {
			console.log('recieved a new callCenterEvent...');
			console.dir(item);
			
			$('body').append('<p>Received a new callCenterEvent...</p>');
			// an item was updated. find it in our list and update it.
			for (var i = 0; i < $scope.testItems.length; i++) {
				if ($scope.testItems[i].id === item.id) {
					$scope.testItems[i] = item;
					break;
				}
			}
			
			console.dir($scope.testItems);
		});
		
		
	}
);