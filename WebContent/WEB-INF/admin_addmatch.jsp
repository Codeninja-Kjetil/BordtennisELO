<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Table Tennis - Admin - Add Match</title>
</head>
<body>
    <%@ include file="header.jspf"%>
    <h1>Admin - Add Match</h1>
    <form action="AdminAddMatch" method="post">
        <table>
            <tr>
                <td>Player 1: </td>
                <td><select name="username1">
                    <option value=""></option>
                    <c:forEach items="${playerlist}" var="p">
                        <option value="${p.username}">${p.name}</option>
                    </c:forEach>
                </select></td>
            </tr>
            <tr>
                <td>Player 2: </td>
                <td><select name="username2">
                    <option value=""></option>
                    <c:forEach items="${playerlist}" var="p">
                        <option value="${p.username}">${p.name}</option>
                    </c:forEach>
                </select></td>
            </tr>
            <tr>
                <td><label>Score: </label></td>
                <td><select name="score">
                    <option value=""></option>
                    <option value="3-0">3-0</option>
                    <option value="3-1">3-1</option>
                    <option value="3-2">3-2</option>
                    <option value="0-3">0-3</option>
                    <option value="1-3">1-3</option>
                    <option value="2-3">2-3</option>
                </select></td>
            </tr>
            <tr>
                <td>Time: </td>
                <td><input type="text" name="time" /></td>
            </tr>
            <tr>
                <td colspan="2">Valid time format is: dd.mm.yy hh:mm</td>
            </tr>
            <tr>
                <td><label>Approved: </label></td>
                <td><select name="approved">
                    <option value=""></option>
                    <option value="0">Approved</option>
                    <option value="1">Waiting for Player 1</option>
                    <option value="2">Waiting for Player 2</option>
                    <option value="-1">Denied by Player 1</option>
                    <option value="-2">Denied by Player 2</option>
                    <option value="-3">Denied by Admin</option>
                </select></td>
            </tr>
        </table>
        <c:if test="${not empty error}">
            <div>${error}</div>
        </c:if>
        <div>
            <button type="submit">Submit</button>
        </div>
    </form>
    
</body>
</html>