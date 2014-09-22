<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Bordtennis - Registrere ny kamp</title>
</head>
<body>
  <h1>Registrere ny kamp</h1>
  <form action="Newmatch" method="post">
    <table>
      <tr>
        <td><label>Motstander: </label></td>
        <td><select name="opponent">
            <option value=""></option>
            <c:forEach items="${playerlist}" var="p">
              <option value="${p.username}">${p.name}</option>
            </c:forEach>
        </select></td>
      </tr>
      <tr>
        <td><label>Resultat: </label></td>
        <td><select name="victor">
            <option value=""></option>
            <option value="1">Seier</option>
            <option value="2">Tap</option>
        </select></td>
      </tr>
      <tr>
        <td><label>Tidspunkt: </label></td>
        <td><input type="text" name="time" /></td>
      </tr>
      <tr>
        <td colspan="2"><button type="submit">Registrer</button></td>
      </tr>
    </table>
  </form>
</body>
</html>