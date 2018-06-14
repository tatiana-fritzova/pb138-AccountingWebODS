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
<jsp:include page="navbar.jsp"/>
<div class="container" align="center">
    <h1>List of invoices</h1>
    <div class="container">
        <input class="form-control col-md-6" id="searchInput" type="text" placeholder="Search...">
        <br>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Type</th>
                <th>From</th>
                <th>To</th>
                <th>Items</th>
                <th>Issue date</th>
                <th>Due date</th>
                <th>Total amount</th>
            </tr>
            </thead>
            <tbody id="itemsTable">
            <c:forEach items="${invoices}" var="invoice">
                <tr>
                    <td><c:out value="${invoice.type}"/></td>
                    <td><c:out value="${invoice.billFrom.name}"/>, <c:out value="${invoice.billFrom.address}"/></td>
                    <td><c:out value="${invoice.billTo.name}"/>, <c:out value="${invoice.billTo.address}"/></td>
                    <td>
                        <c:forEach items="${invoice.items}" var="item">
                            <c:out value="${item.description}"/> (<c:out value="${item.price}"/> EUR) <br>
                        </c:forEach>
                    </td>
                    <td><c:out value="${invoice.issueDate}"/></td>
                    <td><c:out value="${invoice.dueDate}"/></td>
                    <td><c:out value="${invoice.getTotalAmount()}"/> EUR</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div>


    <script>
        $(document).ready(function(){
            $("#searchInput").on("keyup", function() {
                var value = $(this).val().toLowerCase();
                $("#itemsTable tr").filter(function() {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                });
            });
        });
    </script>


</div>
</body>
</html>
