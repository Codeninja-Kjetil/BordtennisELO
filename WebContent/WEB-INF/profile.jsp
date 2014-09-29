<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Table tennis - Profile</title>

<script src="js/lib/jquery-1.11.1.js" type="text/javascript"></script>
<script src="js/lib/highcharts.js" type="text/javascript"></script>
<script type="text/javascript">var chartdata = ${elochart};</script>
<script type="text/javascript" src="js/elochart.js"></script>

</head>
<body>
  <h1>Profile</h1>
  <h2>Info</h2>
  <table>
    <tr>
      <td>Name:</td>
      <td>${profilePlayer.name}</td>
    </tr>
    <tr>
      <td>Elo-rating:</td>
      <td>${profilePlayer.elo}</td>
    </tr>
  </table>
  <h2>Elo-rating over time</h2>
  <div id="elochart"></div>
  <c:if test="${loggedIn}">
    <h2>Matches to approve</h2>
    <c:choose>
      <c:when test="${pending.isEmpty()}">
        <p>No matches need approving.</p>
      </c:when>
      <c:otherwise>
        <table>
          <tr>
            <th>Opponent</th>
            <th>Time</th>
            <th>Victory/Loss</th>
            <th>Approve/Reject</th>
          </tr>
          <c:forEach items="${pending}" var="m">
            <tr>
              <td>${m.opponent}</td>
              <td>${m.formatTime}</td>
              <td>${m.victor ? "Victory" : "Loss"}</td>
              <td><form method="post" action="Acceptresult">
                  <input type="hidden" name="resultid" value="${m.resultid}" />
                  <button type="submit" name="method" value="accept">Approve</button>
                  <button type="submit" name="method" value="deny">Reject</button>
                </form></td>
            </tr>
          </c:forEach>
        </table>
      </c:otherwise>
    </c:choose>
  </c:if>
</body>
</html>