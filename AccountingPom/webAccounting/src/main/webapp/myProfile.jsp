<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <h1>My profile</h1>
    <br>
    <c:if test="${not empty failure}">
        <div class="alert alert-danger alert-dismissible">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>Failure!</strong> <c:out value="${failure}"/>
        </div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="alert alert-success alert-dismissible">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>Success!</strong> <c:out value="${success}"/>
        </div>
    </c:if>
    <c:if test="${not empty noowner}">
        <div class="alert alert-danger alert-dismissible">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>Failure!</strong> <c:out value="${noowner}"/>
        </div>
    </c:if>
    <form class="form-inline" action="${pageContext.request.contextPath}/myProfile" method="post">
        <div class="form-group">
            <label>Full name:</label>
            <input type="text" class="form-control" name="ownerFullName" value="<c:out value="${currentName}"/>" required>
        </div>
        <div class="form-group">
            <label>Address:</label>
            <input type="text" class="form-control" name="ownerAddress" value="<c:out value="${currentAddress}"/>" required>
        </div>
        <button type="submit" class="btn btn-default">Set new owner</button>
    </form>

</Added new invoice.
        </div>
</body>
</html>
