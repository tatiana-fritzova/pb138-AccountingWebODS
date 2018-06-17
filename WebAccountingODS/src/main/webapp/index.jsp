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
<div class="jumbotron center">
    <div class="container">
        <h1 class="welcome">
            Welcome to <br>
            <strong><i>Accounting Management System</i></strong>
        </h1>
    </div>
</div>

<div class="container">
    <div class="list-group">
        <div class="list-group-item list-group-item-info">Choose action</div>
        <a href="/myProfile" class="list-group-item">My profile</a>
        <a href="/newInvoice" class="list-group-item">Add new invoice</a>
        <a href="/viewInvoices" class="list-group-item">View invoices</a>
        <a href="/balances" class="list-group-item">Balances for accounting years</a>
        <a href="/export" class="list-group-item">Export to PDF</a>
    </div>

</div>
</body>
</html>
