<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Table Tennis - Admin - Edit Inactive Limit</title>
</head>
<body>
    <%@ include file="header.jspf"%>
    <h1>Admin - Edit Inactive Limit</h1>
    <p>
        If a player has not played a match in a specific time period, 
        that player is considered inactive. The "Inactive Limit" determines 
        how long this time period is (in months).
    </p>
    <form action="AdminEditInactiveLimit" method="post">
        Inactive Limit (in months):
        <input type="text" name="inactiveLimit" value="${inactiveLimit}" /><br />
        <c:if test="${not empty error}">
            <span>${error}</span><br />
        </c:if>
        <button type="submit">Submit</button>
    </form>
</body>
</html>