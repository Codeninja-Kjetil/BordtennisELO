<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Table tennis - Admin - Edit Player</title>
</head>
<body>
    <h1>Admin - Edit Player</h1>
    <form action="Admin/EditPlayer" method="post">
    <table>
        <tr><td>Username: </td><td>${player.username}</td></tr>
        <tr><td><label></label></td><td></td></tr>
    </table>
    </form>
</body>
</html>