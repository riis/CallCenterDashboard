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
	function DashboardController($rootScope, $scope, $location, $routeParams, agentsService) {
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
		}, function () {
			// Error...
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
		}, function () {
			// Error...
		});
		
		
	}
);