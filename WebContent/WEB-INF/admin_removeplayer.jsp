<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Table tennis - Admin - Remove Player</title>
</head>
<body>
    <%@ include file="header.jspf" %>
    <h1>Admin - Remove Player</h1>
    <p>
        You are about to remove the user "${user.username}".
        This will remove all data about this user,
        including all matches the user has played.
        This action is permanent and can't be undone.
        Are you sure you want to do this?
    </p>
    <form action="AdminRemovePlayer" method="post">
        <p>
            <input type="hidden" name="user" value="${user.username}" />
            <label>Your password: </label>
            <input type="password" name="password" />
        </p>
        <c:if test="${not empty error}">
            <p>${error}</p>
        </c:if>
        <p>
            <button type="submit">Yes, do it</button>
            <a href="AdminPlayerList">No, return me to the playerlist</a>
        </p>
    </form>
</body>
</html>