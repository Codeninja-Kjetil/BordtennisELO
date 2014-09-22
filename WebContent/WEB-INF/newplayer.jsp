<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Bordtennis - Ny Spiller</title>
</head>
<body>
	<h1>Ny Spiller</h1>
	<form method="post" action="NewPlayer">
		<table>
			<tr>
				<td><label>Brukernavn: </label></td>
				<td><input type="text" name="user" /></td>
			</tr>
			<tr>
				<td><label>Passord: </label></td>
				<td><input type="password" name="pass1" /></td>
			</tr>
			<tr>
				<td><label>Passord igjen: </label></td>
				<td><input type="password" name="pass2" /></td>
			</tr>
			<tr>
				<td><label>Navn: </label></td>
				<td><input type="text" name="name" /></td>
			</tr>
			<c:if test="${error != null && !error.isEmpty()}">
				<tr>
					<td colspan="2">${error}</td>
				</tr>
			</c:if>
			<tr>
				<td colspan="2"><button type="submit">Registrer</button>
					<button type="reset">Reset</button></td>
			</tr>
		</table>
	</form>
</body>
</html>