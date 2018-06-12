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
<div ng-app="itemList" ng-controller="myCtrl">

<div class="container">
    <form action="${pageContext.request.contextPath}/newInvoice" method="post" name="createForm" id="createForm">

        <div id="itemInputs">
            <input ng-repeat="x in products" name="itemName" value="{{x[0]}}" hidden>
            <input ng-repeat="x in products" name="{{x[0]}}Price" value="{{x[1]}}" hidden>
            <input ng-repeat="x in products" name="{{x[0]}}Pieces" value="{{x[2]}}" hidden>
        </div>

        <button type="submit" class="btn btn-primary">
            <h4 id="create">New Invoice</h4>
        </button>

        <div class="row">
            <div class="col-md-6">
                <label class="control-label" for="sel1">Invoice type:</label>
                <select class="form-control" id="sel1">
                    <option value="expense">Expense</option>
                    <option value="income">Income</option>
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
    </form>

        <h4>Items</h4>
        <div class="row">
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-addon"><i class="material-icons">description</i></span>
                    <input ng-model="item" type="text" id="description" class="form-control" name="itemDescription" placeholder="Description">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="input-group">
                    <span class="input-group-addon"><i class="material-icons">euro_symbol</i></span>
                    <input ng-model="price" type="number" id="price" class="form-control" name="itemPrice" placeholder="Price"
                           min="0" max="100" step="0.01">
                </div>
            </div>
            <div class="col-sm-2">
                <div class="input-group">
                    <span class="input-group-addon"><i class="material-icons">exposure</i></span>
                    <input ng-model="pieces" type="number" id="pieces" class="form-control" name="itemPrice" min="0" placeholder="Pieces">
                </div>
            </div>
            <div class="col-sm-2">
                <button ng-click="addItem()" type="button" class="btn btn-info" >
                    <i class="material-icons">add</i>
                </button>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12">
                <div class="table-responsive"></div>
                <table class="table table-hover">
                    <thead>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Pieces</th>
                    <th></th>
                    </thead>
                    <tbody>
                    <tr ng-repeat="x in products">
                        <td>{{x[0]}}</td>
                        <td>{{x[1]}}</td>
                        <td>{{x[2]}} </td>
                        <td><span ng-click="removeItem($index)">×</span></td>
                    </tr>
                    </tbody>
                </table>
                <%--<input ng-model="item" type="text">--%>
                <%--<input ng-model="price" type="number" default="0">--%>
                <%--<input ng-model="pieces" type="number" min="1">--%>
                <%--<button ng-click="addItem()">Add item</button>--%>
                <p>Total: {{sum}}</p>
            </div>
        </div>
</div>

</div>

<script src = "https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script>
    var app = angular.module("itemList", []);
    app.controller("myCtrl", function($scope) {
        $scope.products = [["Milk",20,1],["Bread",15,1],["Cheese",25,1]];
        $scope.names = ["Milk","Bread","Cheese"];
        $scope.sum = 60;
        $scope.addItem = function () {
            if (!$scope.item | !$scope.price | !$scope.pieces) {
                alert("All fields describing item must be filled.");
                return;
            }
            if ($scope.names.indexOf($scope.item) > -1) {
                alert("Product has been added already.");
                return;
            }
            $scope.products.push([$scope.item,$scope.price,$scope.pieces]);
            $scope.names.push($scope.item);
            $scope.sum += $scope.price * $scope.pieces;
        }
        $scope.removeItem = function (x) {
            $scope.sum -= $scope.products[x][1] * $scope.products[x][2]
            $scope.products.splice(x, 1);
            $scope.names.splice(x, 1);
        }
    });
</script>
</body>
</html>