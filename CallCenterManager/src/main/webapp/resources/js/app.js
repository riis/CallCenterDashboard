/*global angular, $, document, window*/

'use strict';
var roadrunnerapp = angular.module('roadrunnerapp', [
    'ngRoute',
    'ngResource',
    'roadrunner.home',
    'ui.router'
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

.config(function myAppConfig($stateProvider, $urlRouterProvider, $httpProvider) {
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];

    $urlRouterProvider.otherwise('/dashboard');
})

    .run(function run() {})
    
    .controller('AppCtrl', function AppCtrl($rootScope, $scope, $location) {
    });


angular.element(document).ready(function () {
	angular.bootstrap(document, ['roadrunnerapp']);
});