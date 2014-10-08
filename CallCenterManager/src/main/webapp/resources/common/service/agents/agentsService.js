Services.service('agentsService', function ($http, agentsFactory) {
	var service = {};
	
	
	/**
	 * Get Agents..
	 */
	service.getAgents = function (successCallback, errorCallback) {
		// Get the agents from the web service
		$http.get('/CallCenterManager/webservices/agentList').success(successCallback).error(errorCallback);
	}

	/**
	 * Get Calls Center List..
	 */
	service.getCallCenters = function (successCallback, errorCallback) {
		// Get the agents from the web service
		$http.get('/CallCenterManager/webservices/callCenterList').success(successCallback).error(errorCallback);
	}
	
	/**
	 * Get the agent summary
	 */
	service.getAgentSummary = function (successCallback, errorCallback) {
		// Get the agents from the web service
		$http.get('/CallCenterManager/webservices/callCenterAgentSummary').success(successCallback).error(errorCallback);
	}
	return service;
});