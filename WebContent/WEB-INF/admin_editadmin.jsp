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
<title>Table tennis - Admin - Edit Admin</title>
</head>
<body>
    <%@ include file="header.jspf" %>
    <h1>Admin - Edit Admin</h1>
    <p>
        <c:choose>
            <c:when test="${user.admin}">
                You are about to remove administrator rights from the user "${user.username}".
                This means that the user can't do any admin only actions anymore.
                This action can be undone. Are you sure you want to do this?
            </c:when>
            <c:otherwise>
                You are about to give administrator rights to the user "${user.username}".
                This means that the user can do everything an admin can do, 
                including removing admin rights from other admins.
                This action can be undone. Are you sure you want to do this?
            </c:otherwise>
        </c:choose>
    </p>
    <form action="AdminEditAdmin" method="post">
        <p>
            <input type="hidden" name="user" value="${user.username}" />
            <label>Your password: </label>
            <input type="password" name="password" />
        </p>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <div>
            <button type="submit">Yes, do it</button>
            <a href="AdminPlayerList">No, return me to the playerlist</a>
        </div>
    </form>
</body>
</html>