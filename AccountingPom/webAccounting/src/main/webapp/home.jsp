<%--
  Created by IntelliJ IDEA.
  User: tatiana
  Date: 11.6.2018
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Accounting</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">
    <link rel="stylesheet" href="/homeStyle.css"/>
</head>
<body>
<div class="jumbotron">
    <div class="container">
        <h1 class="welcome">
            Welcome to <br>
            <strong><i>Accounting Management System</i></strong>
        </h1>
    </div>
</div>
<div class="container center">
    <div class="row center activities">
        <div class="col-md-6">
            <div class="row">
                <div class="col-sm-6 nav-link"><a href="myProfile.jsp">My profile</a></div>
                <div class="col-sm-6 nav-link"><a href="newInvoice.jsp">Add new invoice</a></div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="row">
                <div class="col-sm-6 nav-link"><a href="viewInvoices.jsp">View invoices</a></div>
                <div class="col-sm-6 nav-link"><a href="export.jsp">Export to PDF</a></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
