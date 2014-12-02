<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Table tennis - Login</title>
</head>
<body>
    <%@ include file="header.jspf" %>
	<h1>Login</h1>
	<form method="post" action="Login">
		<table>
			<tr>
				<td><label>Username: </label></td>
				<td><input type="text" name="user" /></td>
			</tr>
			<tr>
				<td><label>Password: </label></td>
				<td><input type="password" name="pass" /></td>
			</tr>
		</table>
        <c:if test="${not empty error}">
            <div>${error}</div>
        </c:if>
        <div>
            <button type="submit">Login</button>
        </div>
	</form>
</body>
</html>