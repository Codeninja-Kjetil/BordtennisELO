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
<title>Table tennis - New Player</title>
</head>
<body>
    <%@ include file="header.jspf" %>
	<h1>New Player</h1>
	<form method="post" action="NewPlayer">
		<table>
			<tr>
				<td><label>Username: </label></td>
				<td><input type="text" name="user" /></td>
			</tr>
			<tr>
				<td><label>Password: </label></td>
				<td><input type="password" name="pass1" /></td>
			</tr>
			<tr>
				<td><label>Password again: </label></td>
				<td><input type="password" name="pass2" /></td>
			</tr>
			<tr>
				<td><label>Name: </label></td>
				<td><input type="text" name="name" /></td>
			</tr>
            <tr>
                <td><label>E-mail: </label></td>
                <td><input type="text" name="email" /></td>
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