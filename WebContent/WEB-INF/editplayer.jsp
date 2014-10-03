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
    <h1>Edit User Data</h1>
    <form method="post" action="EditPlayer">
        <table>
            <tr>
                <td><label>New Password: </label></td>
                <td><input type="password" name="newpass1" /></td>
            </tr>
            <tr>
                <td><label>New Password again: </label></td>
                <td><input type="password" name="newpass2" /></td>
            </tr>
            <tr>
                <td><label>New E-mail: </label></td>
                <td><input type="text" name="newemail" /></td>
            </tr>
            <tr>
                <td colspan="2">Type inn old password to edit you user data:</td>
            </tr>
            <tr>
                <td><label>Old Password: </label></td>
                <td><input type="password" name="oldpass" /></td>
            </tr>
            <c:if test="${error != null && !error.isEmpty()}">
                <tr>
                    <td colspan="2">${error}</td>
                </tr>
            </c:if>
            <tr>
                <td colspan="2"><button type="submit">Submit</button>
                    <button type="reset">Reset</button></td>
            </tr>
        </table>
    </form>
</body>
</html>