Factories.factory('agentsFactory', function() {
	var factory = {};
	
	/**
	 * This function will re-format the data in which is expected by the controller / view
	 * It will pass the data back to the Service, which was called from the controller
	 * @see agentsService::getAgents()
	 */
	factory.getAgents = function (agents) {
		var agentsArray = [];
		for(var i=0; i < agents.length; i++) {
			agentsArray.push({
				extension: agents[i].extension,
				agentId: agents[i].agentId,
				callCenterId: agents[i].callCenterId,
				status: 1,
				time: 10
			});
		}
		return agentsArray;
	}
	
	return factory;
});