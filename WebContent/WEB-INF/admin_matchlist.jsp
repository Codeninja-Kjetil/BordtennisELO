<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="css/tabletennis.css" />
<title>Table tennis - Admin - Match list</title>
</head>
<body class="wide">
    <%@ include file="header.jspf"%>
    <h1>Admin - Match list</h1>
    <table class="table">
        <tr>
            <th>ID</th>
            <th>Player 1</th>
            <th>Player 2</th>
            <th>Time</th>
            <th>Score</th>
            <th>Approved?</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${matchlist}" var="m">
            <tr>
                <td>${m.match.matchid}</td>
                <td>${m.player1}</td>
                <td>${m.player2}</td>
                <td>${m.match.timeString}</td>
                <td>${m.match.score}</td>
                <c:choose>
                    <c:when test="${m.match.approved == 0}"><td class="green">Approved</td></c:when>
                    <c:when test="${m.match.approved == -3}"><td class="red">Admin denied</td></c:when>
                    <c:when test="${m.match.approved > 0}"><td class="yellow">Waiting for Player ${m.match.approved}</td></c:when>
                    <c:when test="${m.match.approved < 0}"><td class="red">Player ${- m.match.approved} denied</td></c:when>
                </c:choose>
                <td><a href="AdminEditMatch?matchid=${m.match.matchid}">Edit</a></td>
                <td><a href="AdminRemoveMatch?matchid=${m.match.matchid}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <p><a href="AdminAddMatch">New Match</a></p>
</body>
</html>