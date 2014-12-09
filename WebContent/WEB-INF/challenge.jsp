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
<title>Table tennis - Challenge</title>
</head>
<body>
    <%@ include file="header.jspf" %>
    <h1>Challenge</h1>
    <form action="Challenge" method="post">
        <div>Opponent: 
            <select name="opponent">
                <option value=""></option>
                <c:forEach items="${playerlist}" var="p">
                    <option value="${p.username}">${p.name}</option>
                </c:forEach>
            </select>
        </div>
        <div>Message:<br />
            <textarea name="message" rows="8" cols="50"></textarea>
        </div>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <div>
            <button type="submit">Send</button>
        </div>
    </form>
</body>
</html>