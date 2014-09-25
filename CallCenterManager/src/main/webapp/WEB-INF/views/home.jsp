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
            Call Center Id = <c:out value="${callCenter.callCenterId}"/> Call Center Name = <c:out value="${callCenter.callCenterName}"/> Calls In Queue  = <C:out value="${callCenter.queueLength}"/><br><br>
        </c:forEach>
        <p>Page Last Refreshed <c:out value="${model.now}"/></p>
    </body>
</html>
