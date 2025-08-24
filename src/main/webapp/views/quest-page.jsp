<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link href="../css/main.css" rel="stylesheet">
    <title>Title</title>
</head>
<body>
<h2>${sessionScope.scene.getText()}</h2>
<br>
<c:if test="${sessionScope.scene.getCode() != \"sLose\" && sessionScope.scene.getCode() != \"sWin\"}">
<form method="get" action="controller">
    <input type="submit" name="mode" value="Yes">
    <input type="submit" name="mode" value="No">
</form>
</c:if>
<c:if test="${sessionScope.scene.getCode() == \"sLose\"}">
    <form method="get" action="controller">
        <input type="submit" name="mode" value="Restart">
    </form>
</c:if>
<c:if test="${sessionScope.scene.getCode() == \"sWin\"}">
    <form method="get" action="controller">
        <input type="submit" name="mode" value="Finish">
    </form>
</c:if>
</body>
</html>
