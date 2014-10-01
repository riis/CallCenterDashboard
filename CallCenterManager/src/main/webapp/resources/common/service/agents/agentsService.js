Services.service('agentsService', function (agentsFactory) {
	var service = {};
	
	
	/**
	 * Get Agents..
	 */
	service.getAgents = function () {

		// TODO: Make web service call
		var agents = [];
		agents.push({
			extension: '123',
			agentId: '555222',
			callCenterId: '33'
		});
		agents.push({
			extension: '122',
			agentId: '555221',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		agents.push({
			extension: '124',
			agentId: '555223',
			callCenterId: '33'
		});
		
		console.log('about to call the agent factory..');
		return agentsFactory.getAgents(agents);
	}
	
	return service;
});