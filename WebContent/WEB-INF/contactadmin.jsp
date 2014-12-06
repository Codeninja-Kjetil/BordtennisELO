<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Table Tennis - Contact Admin</title>
</head>
<body>
    <%@ include file="header.jspf" %>
    <h1>Contact Admin</h1>
    <p>
        Use this form to send a message to the administrators. 
        The message will be sent to all current admins 
        and will include your username, name and e-mail address.
    </p>
    <form action="ContactAdmin" method="post">
        <div><textarea name="message" rows="12" cols="80"></textarea></div>
        <c:if test="${not empty error}">
            <div>${error}</div>
        </c:if>
        <div><button type="submit">Send</button></div>
    </form>
</body>
</html>