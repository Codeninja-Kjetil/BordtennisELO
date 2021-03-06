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
<title>Table tennis - Admin - Lock Player</title>
</head>
<body>
    <%@ include file="header.jspf" %>
    <h1>Admin - Lock Player</h1>
    <p>
        <c:choose>
            <c:when test="${user.locked}">
                You are about to unlock the user "${user.username}".
                This means that the user is able to log in again.
                This action can be undone. Are you sure you want to do this?
            </c:when>
            <c:otherwise>
                You are about to lock the user "${user.username}".
                This means that the user can't log in anymore,
                but all his/her data is still preserved.
                This action can be undone. Are you sure you want to do this?
            </c:otherwise>
        </c:choose>
    </p>
    <form action="AdminLockPlayer" method="post">
        <input type="hidden" name="user" value="${user.username}" />
        <button type="submit">Yes, do it</button>
        <a href="AdminPlayerList">No, return me to the playerlist</a>
    </form>
</body>
</html>