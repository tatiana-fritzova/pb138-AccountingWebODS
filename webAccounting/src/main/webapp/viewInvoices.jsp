<%--
  Created by IntelliJ IDEA.
  User: tatiana
  Date: 11.6.2018
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Accounting - View Invoices</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">
</head>
<body>
<h1>
<c:choose>
    <c:when test="${empty error}}">
        <div class="alert alert-danger">
            <strong>Info!</strong> no invoices
        </div>
    </c:when>
    <c:otherwise>
        <div class="alert alert-info">
            <strong>Info!</strong> there are some invoices
        </div>
    </c:otherwise>
</c:choose>

</h1>
<jsp:include page="navbar.jsp"/>
<div class="container" align="center">
    <h1>Accounting history</h1>
    <form action="viewInvoices/">
        First name:<br>
        <input type="text" name="firstname" value="Mickey">
        <br>
        Last name:<br>
        <input type="text" name="lastname" value="Mouse">
        <br><br>
        <input type="submit" value="Submit">
    </form>
    <table class="table table-hover" id="items">
        <thead>
        <tr>
            <td>Description</td>
            <td>Price per item</td>
            <td>Pieces</td>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${seznam}" var="bla">
                <tr>
                    <td><c:out value="${bla}"/></td>
                    <td>-</td>
                    <td>-</td>
                </tr>
            </c:forEach>
        <%--generate a row for each invoice--%>
        <%--items in data-toggle / hidden--%>
        <%--https://www.w3schools.com/bootstrap/bootstrap_filters.asp--%>
        </tbody>
    </table>
</div>
</body>
</html>
