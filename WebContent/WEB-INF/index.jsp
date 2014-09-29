<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8" />
<title>Table tennis</title>
</head>
<body>
    <h1>Table tennis</h1>
    <c:choose>
        <c:when test="${player != null}">
            <p>
                Logged in as:
                <a href="Profile?user=${player.username}">${player.name}</a>
                <a href="Logout">Log out</a>
            </p>
        </c:when>
        <c:otherwise>
            <p>
                <a href="Login">Login</a> <a href="NewPlayer">Register</a>
            </p>
        </c:otherwise>
    </c:choose>
    <table>
        <tr>
            <th>Name</th>
            <th>Elo-rating</th>
        </tr>
        <c:forEach items="${players}" var="p">
            <tr>
                <td><a href="Profile?user=${p.username}">${p.name}</a></td>
                <td>${p.elo}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>