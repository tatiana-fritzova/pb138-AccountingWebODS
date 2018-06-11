<%--
  Created by IntelliJ IDEA.
  User: tatiana
  Date: 11.6.2018
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Accounting - Export to PDF</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">
</head>
<body>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <h1>Export accounting data</h1>
    <form >
        <div class="form-group form-inline">
            <label class="control-label">Choose accounting year:</label>
            <select name="year" class="form-control select">
                <option value="">all</option>
                <%--generate available years--%>
                <option value="2018">2018</option>
                <option value="2017">2017</option>
            </select>
        </div>
        <a href="#" data-toggle="tooltip" data-placement="right" title="PDF will be automatically downloaded.">
            <button type="submit" class="btn btn-primary">Export</button>
        </a>
    </form>
</div>
<script>
    $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>
</body>
</html>