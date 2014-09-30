'use strict';

angular.module('roadrunner.home', [
    'ui.router'
])

/**
 * Each section or module of the site can also have its own routes. AngularJS
 * will handle ensuring they are all available at run-time, but splitting it
 * this way makes each module more "self-contained".
 */
.config(function config($stateProvider) {
    $stateProvider.state('home', {
        url: '/dashboard',
        views: {
            "main": {
                controller: 'HomeCtrl',
                templateUrl: 'resources/views/home/home.tpl.html'
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
.controller('HomeCtrl',
	function HomeController($rootScope, $scope, $location, $routeParams) {
		$scope.pageTitle = 'Dashboard';
	}
);