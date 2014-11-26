<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Table tennis - Admin</title>
</head>
<body>
  <%@ include file="header.jspf" %>
  <h1>Admin</h1>
  <h2>Edit Players</h2>
  <p><a href="AdminPlayerList">Player list</a></p>
  <h2>Edit Matches</h2>
  <p><a href="AdminAddMatch">New Match</a></p>
  <p><a href="AdminMatchList">Match List</a></p>
  <h2>Other</h2>
  <p><a href="AdminEditInactiveLimit">Edit Inactive Limit</a></p>
</body>
</html>