<%--
  Created by IntelliJ IDEA.
  User: tatiana
  Date: 11.6.2018
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Accounting - Add new Invoice</title>
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
<div class="container">
    <form action="/addInvoice" method="post" name="createForm">

        <button type="submit" class="btn btn-primary">
            <h4 id="create">New Invoice</h4>
        </button>

        <div class="row">
            <div class="col-md-6">
                <label class="control-label" for="sel1">Invoice type:</label>
                <select class="form-control" id="sel1">
                    <option>Expense</option>
                    <option>Income</option>
                </select>
            </div>
            <div class="form-group col-md-6">
                <label class="control-label">From/To:</label>
                <input type="text" class="form-control" name="fromTo" required autofocus/>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-6">
                <label class="control-label">Issue date:</label>
                <input type="date" class="form-control" name="issueDate" required/>
            </div>
            <div class="form-group col-md-6">
                <label class="control-label">Due date:</label>
                <input type="date" class="form-control" name="dueDate" required>
            </div>
        </div>
        <h4>Items</h4>
        <div class="row">
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-addon"><i class="material-icons">description</i></span>
                    <input type="text" id="description" class="form-control" name="itemDescription" placeholder="Description">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="input-group">
                    <span class="input-group-addon"><i class="material-icons">euro_symbol</i></span>
                    <input type="number" id="price" class="form-control" name="itemPrice" placeholder="Price"
                           min="0" max="100" step="0.01">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="input-group">
                    <span class="input-group-addon"><i class="material-icons">exposure</i></span>
                    <input type="number" id="pieces" class="form-control" name="itemPrice" min="0" placeholder="Pieces">
                </div>
            </div>
            <div class="col-sm-2">
                <button type="button" class="btn btn-info" onclick="addItem();" >
                    <i class="material-icons">add</i>
                </button>
            </div>
        </div>
    </form>
    <table class="table table-hover" id="items" style="display: none;">
        <thead>
        <tr>
            <td>Description</td>
            <td>Price per item</td>
            <td>Pieces</td>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

<script>
    function addItem() {
        var description = document.getElementById("description").value;
        var pieces = document.getElementById("pieces").value;
        var price = document.getElementById("price").value;
        if (description === "" || pieces === "" || price==="") {
            alert("dkfjslkdjf");
            return;
        }
        document.getElementById("items").style.display = "block";
        $(function(){
            $("#items").find("tbody").append(
                "<tr><td>"+ description +
                "</td><td>" + price +
                "</td><td>" + pieces +"</td></tr>");
        });
    }
</script>
</body>
</html>