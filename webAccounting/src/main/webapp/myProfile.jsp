<%--
  Created by IntelliJ IDEA.
  User: tatiana
  Date: 11.6.2018
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Accounting - My Profile</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container" align="center">
    <form action="#">
        <div class="form-group">
            <label for="name">Name:</label>
            <input type="text" class="form-control" id="name">
        </div>
        <div class="form-group">
            <label for="surname">Surame:</label>
            <input type="text" class="form-control" id="surname">
        </div>
        <div class="form-group">
            <label for="address">Address:</label>
            <input type="text" class="form-control" id="address">
        </div>
        <button type="submit" class="btn btn-default">Save</button>
    </form>

</div>
</body>
</html>
