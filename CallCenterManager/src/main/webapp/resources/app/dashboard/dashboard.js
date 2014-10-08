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

		$scope.pendingAgentUpdates = [];
		
		// Start Loader...
		startLoader('#agents .panel-body');
		
		//---------------------------------------------------------------------------
		// Get the agents from the Web Service
		//---------------------------------------------------------------------------
		var agents = agentsService.getAgents(function (response) {
			// Success...
			$scope.agents = response;

			for (var j = 0; j < $scope.pendingAgentUpdates.length; j++) {
				applyAgentUpdate($scope.pendingAgentUpdates[j]);
			}

			$scope.pendingAgentUpdates = null;

			endLoader('#agents .panel-body');
		}, function (error) {
			// Error...
			endLoader('#agents .panel-body');
			$scope.agentsError = true;
		});
		
		Pusher.subscribe('channel-three', 'agentEvent', function (item) {
			item = JSON.parse(item.agentEvent);

			$scope.dateObj = new Date();

			if ($scope.agents) {
				applyAgentUpdate(item);
			} else {
				$scope.pendingAgentUpdates.push(item);
			}

		});

		function applyAgentUpdate(item) {
			for (var i = 0; i < $scope.agents.length; i++) {
				if ($scope.agents[i].agentId === item.targetId) {
					$scope.agents[i].status = item.state;
				}
			}
		}
		
		
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
		var callCenters = agentsService.getCallCenters(function (response){
			console.log('successding');
			$scope.callCenters = response;
			buildChartObjectData($scope.callCenters);

		}, function (error){
			console.log('failing');
			$scope.callCenterError = true;
		});

		$scope.chartObject = {};
		// $routeParams.chartType == BarChart or PieChart or ColumnChart...
	    $scope.chartObject.type = 'BarChart';
	    $scope.chartObject.options = {};
	    
		function buildChartObjectData(callCenters){
			$scope.chartCols = [
		    	{id: "t", label: "Call Center", type: "string"},
		        {id: "s", label: "Calls In Queue", type: "number"}
		    ];
		    $scope.chartRows = [];

		    callCenters.forEach(function(callCenter){
		    	var tempItem = {};
		    	tempItem.c = [];
		    	tempItem.c.push({v: callCenter.callCenterName});
		    	tempItem.c.push({v: callCenter.queueLength});

		    	$scope.chartRows.push(tempItem);
		    });


		    $scope.chartObject.data = { 
		    	'cols' : $scope.chartCols, 
		    	'rows': $scope.chartRows
		    };
		}

		function updateCallsInQueue(eventData){
			var target = eventData.targetId.substr(0, eventData.targetId.indexOf('@'));
			console.log('Our target is' + target);
			
			// find call center index in the source array 
			var callCenterIndex = -1;
			for( var i = 0; i < $scope.callCenters.length; i++){
				if($scope.callCenters[i].callCenterId == target){
					callCenterIndex = i;
					break;
				}
			}

			// update the calls in queue value in chart array
			$scope.chartRows[callCenterIndex].c[1].v += parseInt(eventData.numCallsInQueue);
		}
	
		Pusher.subscribe('channel-two', 'callCenterEvent', function (item) {
			console.log('recieved a new callCenterEvent...');
			var eventData = JSON.parse(item.callCenterEvent);
			updateCallsInQueue(eventData);
			
			$scope.dateObj = new Date();
		});
	}
);