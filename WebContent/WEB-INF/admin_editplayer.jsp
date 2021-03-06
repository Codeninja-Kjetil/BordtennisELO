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
<title>Table tennis - Admin - Edit Player</title>
</head>
<body>
    <%@ include file="header.jspf"%>
    <h1>Admin - Edit Player</h1>
    <form action="AdminEditPlayer" method="post">
        <table>
            <tr>
                <td>Username:</td>
                <td>${user.username}<input type="hidden"
                    name="user" value="${user.username}" /></td>
            </tr>
            <tr>
                <td>Name:</td>
                <td><input type="text" name="name" value="${user.name}" /></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><input type="text" name="email" value="${user.email}" /></td>
            </tr>
            <tr>
                <td>Private profile:</td>
                <td><select name="privateprofile">
                    <option value="true" ${user.privateprofile ? 'selected="selected"' : ''}>Yes</option>
                    <option value="false" ${user.privateprofile ? '' : 'selected="selected"'}>No</option>
                </select></td>
            </tr>
            <tr>
                <td>New password:</td>
                <td><input type="password" name="newpass1" /></td>
            </tr>
            <tr>
                <td>New password again:</td>
                <td><input type="password" name="newpass2" /></td>
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