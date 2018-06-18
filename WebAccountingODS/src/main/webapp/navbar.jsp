<%--
  Created by IntelliJ IDEA.
  User: tatiana
  Date: 11.6.2018
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/"><b>Accounting</b></a>
        </div>
        <ul class="nav navbar-nav">
            <li class="nav-item"><a href="${param.contextPath}/myProfile">My profile</a></li>
            <li class="nav-item"><a href="${param.contextPath}/newInvoice">Add new invoice</a></li>
            <li class="nav-item"><a href="${param.contextPath}/viewInvoices">View invoices</a></li>
            <li class="nav-item"><a href="${param.contextPath}/balances">Balances</a></li>
            <li class="nav-item"><a href="${param.contextPath}/export">Export to PDF</a></li>
        </ul>
    </div>
</nav>
