/*global angular, $, document, window*/

'use strict';

var Factories = angular.module('roadrunnerapp.factories', []);
var Services = angular.module('roadrunnerapp.services', []);
var Filters = angular.module('roadrunnerapp.filters', []);
var Directives = angular.module('roadrunnerapp.directives', []);

var roadrunnerapp = angular.module('roadrunnerapp', [
    'ngRoute',
    'ngResource',
    'roadrunner.dashboard',
    'roadrunnerapp.factories',
    'roadrunnerapp.services',
    'roadrunnerapp.filters',
    'roadrunnerapp.directives',
    'ui.router',
    'doowb.angular-pusher',
    'googlechart'
], function ($httpProvider) {
    // Use x-www-form-urlencoded Content-Type
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';

    /**
     * The workhorse; converts an object to x-www-form-urlencoded serialization.
     * @param {Object} obj
     * @return {String}
     */
    var param = function (obj) {
        var query = '',
            name,
            value,
            fullSubName,
            subName,
            subValue,
            innerObj,
            i;

        for (name in obj) {
            value = obj[name];

            if (value instanceof Array) {
                for (i = 0; i < value.length; ++i) {
                    subValue = value[i];
                    fullSubName = name + '[' + i + ']';
                    innerObj = {};
                    innerObj[fullSubName] = subValue;
                    query += param(innerObj) + '&';
                }
            } else if (value instanceof Object) {
                for (subName in value) {
                    subValue = value[subName];
                    fullSubName = name + '[' + subName + ']';
                    innerObj = {};
                    innerObj[fullSubName] = subValue;
                    query += param(innerObj) + '&';
                }
            } else if (value !== undefined && value !== null) {
                query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
            }
        }

        return query.length ? query.substr(0, query.length - 1) : query;
    };
})

.config(['PusherServiceProvider', '$stateProvider', '$urlRouterProvider', '$httpProvider',
	function(PusherServiceProvider, $stateProvider, $urlRouterProvider, $httpProvider) {
		PusherServiceProvider.setToken('da81ce797d453db253d1').setOptions({
			
		});

    	$httpProvider.defaults.withCredentials = true;
		$httpProvider.defaults.useXDomain = true;
		delete $httpProvider.defaults.headers.common['X-Requested-With'];

		$urlRouterProvider.otherwise('/dashboard');
	}]
)
.run(function run() {})
.controller('AppCtrl', function AppCtrl($rootScope, $scope, $location) {
    (function() {

        var months = ['January','February','March','April','May','June','July','August','September','October','November','December'];
        var days = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];

        Date.prototype.getMonthName = function() {
            return months[ this.getMonth() ].substring(0,3);
        };
        Date.prototype.getDayName = function() {
            return days[ this.getDay() ];
        };
        Date.prototype.getAMPM = function() {
            return this.toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$3");
        };
        Date.prototype.getMinutesWithLeadingZero = function() {
            return ('0' + this.getMinutes()).slice(-2);
        };

    })();

    $scope.dateObj = new Date();
});


angular.element(document).ready(function () {
	angular.bootstrap(document, ['roadrunnerapp']);
});