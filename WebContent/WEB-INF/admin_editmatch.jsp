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
<title>Table Tennis - Admin - Edit Match</title>
</head>
<body>
    <%@ include file="header.jspf"%>
    <h1>Admin - Edit Match</h1>
    <form action="AdminEditMatch" method="post">
        <input type="hidden" name="matchid" value="${match.match.matchid}" />
        <table>
            <tr>
                <td>Player 1: </td>
                <td><select name="username1">
                    <option value=""></option>
                    <c:forEach items="${playerlist}" var="p">
                        <option value="${p.username}" ${match.player1 eq p.username ? 'selected="selected"' : ''}>${p.name}</option>
                    </c:forEach>
                </select></td>
            </tr>
            <tr>
                <td>Player 2: </td>
                <td><select name="username2">
                    <option value=""></option>
                    <c:forEach items="${playerlist}" var="p">
                        <option value="${p.username}" ${match.player2 eq p.username ? 'selected="selected"' : ''}>${p.name}</option>
                    </c:forEach>
                </select></td>
            </tr>
            <tr>
                <td><label>Score: </label></td>
                <td><select name="score">
                    <option value=""></option>
                    <option value="3-0" ${match.match.score eq '3-0' ? 'selected="selected"' : ''}>3-0</option>
                    <option value="3-1" ${match.match.score eq '3-1' ? 'selected="selected"' : ''}>3-1</option>
                    <option value="3-2" ${match.match.score eq '3-2' ? 'selected="selected"' : ''}>3-2</option>
                    <option value="0-3" ${match.match.score eq '0-3' ? 'selected="selected"' : ''}>0-3</option>
                    <option value="1-3" ${match.match.score eq '1-3' ? 'selected="selected"' : ''}>1-3</option>
                    <option value="2-3" ${match.match.score eq '2-3' ? 'selected="selected"' : ''}>2-3</option>
                </select></td>
            </tr>
            <tr>
                <td>Time: </td>
                <td><input type="text" name="time" value="${match.match.timeString}" /></td>
            </tr>
            <tr>
                <td colspan="2">Valid time format is: dd.mm.yy hh:mm</td>
            </tr>
            <tr>
                <td><label>Approved: </label></td>
                <td><select name="approved">
                    <option value=""></option>
                    <option value="0" ${match.match.approved eq 0 ? 'selected="selected"' : ''}>Approved</option>
                    <option value="1" ${match.match.approved eq 1 ? 'selected="selected"' : ''}>Waiting for Player 1</option>
                    <option value="2" ${match.match.approved eq 2 ? 'selected="selected"' : ''}>Waiting for Player 2</option>
                    <option value="-1" ${match.match.approved eq -1 ? 'selected="selected"' : ''}>Denied by Player 1</option>
                    <option value="-2" ${match.match.approved eq -2 ? 'selected="selected"' : ''}>Denied by Player 2</option>
                    <option value="-3" ${match.match.approved eq -3 ? 'selected="selected"' : ''}>Denied by Admin</option>
                </select></td>
            </tr>
        </table>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <div>
            <button type="submit">Submit</button>
            <button type="reset">Reset</button>
        </div>
    </form>
    
</body>
</html>