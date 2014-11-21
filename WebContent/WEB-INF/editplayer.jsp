<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Table tennis - Edit User Data</title>
</head>
<body>
    <%@ include file="header.jspf" %>
    <h1>Edit User Data</h1>
    <form method="post" action="EditPlayer">
        <table>
            <tr>
                <td><label>Name: </label></td>
                <td><input type="text" name="name" value="${user.name}" /></td>
            </tr>
            <tr>
                <td><label>E-mail: </label></td>
                <td><input type="text" name="email" value="${user.email}" /></td>
            </tr>
            <tr>
                <td>Private profile: </td>
                <td><select name="privateprofile">
                    <option value="true" ${user.privateprofile ? 'selected="selected"' : ''}>Yes</option>
                    <option value="false" ${user.privateprofile ? '' : 'selected="selected"'}>No</option>
                </select></td>
            </tr>
            <tr>
                <td>Email notification: </td>
                <td><select name="notification">
                    <option value="true" ${user.notification ? 'selected="selected"' : ''}>Yes</option>
                    <option value="false" ${user.notification ? '' : 'selected="selected"'}>No</option>
                </select></td>
            </tr>
            <tr>
                <td><label>New Password: </label></td>
                <td><input type="password" name="newpass1" /></td>
            </tr>
            <tr>
                <td><label>New Password again: </label></td>
                <td><input type="password" name="newpass2" /></td>
            </tr>
            <tr>
                <td colspan="2">Type inn old password to edit you user data:</td>
            </tr>
            <tr>
                <td><label>Old Password: </label></td>
                <td><input type="password" name="oldpass" /></td>
            </tr>
        </table>
        <c:if test="${error != null && !error.isEmpty()}">
            <div>${error}</div>
        </c:if>
        <div>
            <button type="submit">Submit</button>
            <button type="reset">Reset</button>
        </div>
    </form>
</body>
</html>