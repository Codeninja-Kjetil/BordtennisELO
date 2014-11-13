<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Table tennis - Admin - Match list</title>
</head>
<body>
    <%@ include file="header.jspf"%>
    <h1>Admin - Match list</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>Player 1</th>
            <th>Player 2</th>
            <th>Time</th>
            <th>Victor</th>
            <th>Approved?</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${matchlist}" var="m">
            <tr>
                <td>${m.match.matchid}</td>
                <td>${m.player1}</td>
                <td>${m.player2}</td>
                <td>${m.match.time}</td>
                <td>${(m.match.victor == 1 ? "Player 1" : "Player 2")}</td>
                <td>
                    <c:choose>
                        <c:when test="${m.match.approved == 0}">Approved</c:when>
                        <c:when test="${m.match.approved > 0}">Waiting for Player ${m.match.approved}</c:when>
                        <c:when test="${m.match.approved < 0}">Player ${- m.match.approved} denied</c:when>
                    </c:choose>
                </td>
                <td><a href="AdminEditMatch?matchid=${m.match.matchid}">Edit</a></td>
                <td><a href="AdminRemoveMatch?matchid=${m.match.matchid}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>