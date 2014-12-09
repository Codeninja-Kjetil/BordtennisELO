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
<title>Table tennis - Admin - Player list</title>
</head>
<body class="wide">
    <%@ include file="header.jspf"%>
    <h1>Admin - Player list</h1>
    <table class="table">
        <tr>
            <th>User Name</th>
            <th>Name</th>
            <th>Edit</th>
            <th>Lock</th>
            <th>Admin</th>
            <th>Remove</th>
        </tr>
        <c:forEach items="${playerlist}" var="player">
            <tr>
                <td>${player.username}</td>
                <td>${player.name}</td>
                <td><a
                    href="AdminEditPlayer?user=${player.username}">Edit</a></td>
                <td><a
                    href="AdminLockPlayer?user=${player.username}">${player.locked ? "Unlock" : "Lock"}</a></td>
                <td><a
                    href="AdminEditAdmin?user=${player.username}">${player.admin ? "Deadmin" : "Admin"}</a></td>
                <td><a
                    href="AdminRemovePlayer?user=${player.username}">Remove</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>