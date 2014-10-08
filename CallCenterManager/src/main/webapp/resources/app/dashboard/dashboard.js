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
		
		Pusher.subscribe('channel-three', 'agentEvent', function (item) {
			console.log('Received Agent Event: ');
			//item = JSON.parse(item);
			console.dir(item.agentEvent);
			console.log(item.agentEvent.targetId);
			
			// console.log('Event ID:' + item.eventId);
			// console.log('Target ID:' + item.targetId);
			// console.log('Sequence Number:' + item.sequenceNumber);
			

			$scope.dateObj = new Date();

//			$('#'+item.agentId).find('.status').html(item.status);
//			$('#'+item.agentId).find('.extension').html(item.extension);
			
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
		$scope.pendingCallCenterEvents = [];

		setTimeout(function(){
			var callCenters = agentsService.getCallCenters(function (response){
				console.log('successding');
				$scope.callCenters = response;
				buildChartObjectData($scope.callCenters);
				applyPendingCallCenterEvents();

			}, function (error){
				console.log('failing');
				$scope.callCenterError = true;
			});
		}, 10000);

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
			console.log('Updating calls in queue count');
			
			// find the call center index in the source array
			var callCenterIndex = -1;
			for( var i = 0; i < $scope.callCenters.length; i++){
				if($scope.callCenters[i].callCenterId == eventData.targetId){
					callCenterIndex = i;
					break;
				}
			}

			// update the calls in queue value in chart array
			$scope.chartRows[callCenterIndex].c[1].v = parseInt(eventData.numCallsInQueue);
		}

		function applyPendingCallCenterEvents(){
			for(var i = 0; i < $scope.pendingCallCenterEvents.length; i++){
				updateCallsInQueue($scope.pendingCallCenterEvents[i]);
			}
			$scope.pendingCallCenterEvents = null;
		}
		
		Pusher.subscribe('channel-two', 'callCenterEvent', function (item) {
			console.log('recieved a new callCenterEvent...');
			var eventData = JSON.parse(item.callCenterEvent);
			
			if($scope.callCenters){
				updateCallsInQueue(eventData);
			}else{
				$scope.pendingCallCenterEvents.push(eventData);
			}
			
			$scope.dateObj = new Date();
		});
	}
);