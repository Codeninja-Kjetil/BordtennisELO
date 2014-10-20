<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Table tennis - Upload Profile Image</title>
</head>
<body>
    <%@ include file="header.jspf"%>
    <h1>Upload Profile Image</h1>
    <p>Valid image file formats is: .jpg/.jpeg, .png, .bmp and .gif</p>
    <form method="post" action="UploadProfileImage" enctype="multipart/form-data">
        File: <input type="file" name="file" id="file" />
        <button type="submit">Upload</button>
    </form>
</body>
</html>