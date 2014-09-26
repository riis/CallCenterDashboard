<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <p>This is the homepage!</p>
        <h3>CallCenters</h3>
        <c:forEach items="${model.callcenters}" var="callCenter">
            Call Center Id = <i><c:out value="${callCenter.callCenterId}"/></i>, Call Center Name = <i><c:out value="${callCenter.callCenterName}"/></i>, Calls In Queue  = <i><C:out value="${callCenter.queueLength}"/></i><br><br>
        </c:forEach>
        <h3>Agents</h3>
        <c:forEach items="${model.agents}" var="agent">
            Extension = <i><c:out value="${agent.extension}"/></i>, AgentId = <i><c:out value="${agent.agentId}"/></i>, Call Center Id = <i><c:out value="${agent.callCenterId}"/></i><br><br>
        </c:forEach>
        <p>Page Last Refreshed <c:out value="${model.now}"/></p>
    </body>
</html>
