<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Квест проект</title>
</head>
<body>
<h1>Добро пожаловать в квест!</h1>
<br/>
<h2>Чтобы перейти к игре, нажми на кнопку "Start"</h2>
<br/>
<form method="get" action="controller">
    <input type="submit" name="mode" value="Start">
</form>
<br/>

<h3>Имя: ${sessionScope.username} </h3>
<h3>Побед: ${sessionScope.countWins}</h3>
<h3>Количество попыток: ${sessionScope.countTries}</h3>
</body>
</html>
