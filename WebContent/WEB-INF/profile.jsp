<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Bordtennis - Profil</title>

<script src="js/lib/jquery-1.11.1.js" type="text/javascript"></script>
<script src="js/lib/highcharts.js" type="text/javascript"></script>
<script type="text/javascript">var chartdata = ${elochart};</script>
<script type="text/javascript" src="js/elochart.js"></script>

</head>
<body>
  <h1>Profil</h1>
  <h2>Info</h2>
  <table>
    <tr>
      <td>Navn:</td>
      <td>${player.name}</td>
    </tr>
    <tr>
      <td>Elo:</td>
      <td>${player.elo}</td>
    </tr>
  </table>
  <h2>Elo-rating over tid</h2>
  <div id="elochart"></div>
  <h2>Kamper du må godkjenne</h2>
  <c:choose>
    <c:when test="${pending.isEmpty()}">
      <p>Ingen kamper som krever godkjenning.</p>
    </c:when>
    <c:otherwise>
      <table>
        <tr>
          <th>Motstander</th>
          <th>Tidspunkt</th>
          <th>Seier/Tap</th>
          <th>Godkjenn/Avvis</th>
        </tr>
        <c:forEach items="${pending}" var="m">
          <tr>
            <td>${m.opponent}</td>
            <td>${m.formatTime}</td>
            <td>${m.victor ? "Seier" : "Tap"}</td>
            <td><form method="post" action="Acceptresult">
                <input type="hidden" name="resultid" value="${m.resultid}" />
                <button type="submit" name="method" value="accept">Godkjenn</button>
                <button type="submit" name="method" value="deny">Avvis</button>
              </form></td>
          </tr>
        </c:forEach>
      </table>
    </c:otherwise>
  </c:choose>
</body>
</html>