<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Table tennis - Admin - Playerlist</title>
</head>
<body>
  <h1>Admin - Playerlist</h1>
  <table>
    <tr>
      <th>User Name</th>
      <th>Name</th>
      <th>Edit</th>
      <th>Lock</th>
      <th>Remove</th>
    </tr>
    <c:forEach items="${playerlist}" var="player">
      <tr>
        <td>${player.username}</td>
        <td>${player.name}</td>
        <td><a href="Admin/EditPlayer?user=${player.username}">Edit</a></td>
        <td><a href="Admin/LockPlayer?user=${player.username}">Lock</a></td>
        <td><a href="Admin/RemovePlayer?user=${player.username}">Remove</a></td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>