<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8" />
<link rel="stylesheet" type="text/css" href="css/tabletennis.css" />
<title>Table tennis</title>
</head>
<body>
    <%@ include file="header.jspf" %>
    <p><a href="pdf/rules.pdf">Rules (pdf)</a></p>
    <p><a href="DownloadRankings">Ranking-list (pdf)</a></p>
    <c:if test="${not empty activePlayers}">
        <h2>Active players</h2>
        <table class="table">
            <tr>
                <th>Name</th>
                <th>Elo-rating</th>
                <th>Last seen</th>
            </tr>
            <c:forEach items="${activePlayers}" var="p">
                <tr>
                    <td><a href="Profile?user=${p.player.username}">${p.player.name}</a></td>
                    <td>${p.elo}</td>
                    <td>${p.latestMatchTimeString}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${not empty inactivePlayers}">
        <h2>Inactive players</h2>
        <table class="table">
            <tr>
                <th>Name</th>
                <th>Elo-rating</th>
                <th>Last seen</th>
            </tr>
            <c:forEach items="${inactivePlayers}" var="p">
                <tr>
                    <td><a href="Profile?user=${p.player.username}">${p.player.name}</a></td>
                    <td>${p.elo}</td>
                    <td>${p.latestMatchTimeString}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${not empty newPlayers}">
    <h2>New players</h2>
        <table class="table">
            <tr>
                <th>Name</th>
                <th>Elo-rating</th>
            </tr>
            <c:forEach items="${newPlayers}" var="p">
                <tr>
                    <td><a href="Profile?user=${p.username}">${p.name}</a></td>
                    <td>${p.elo}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</body>
</html>