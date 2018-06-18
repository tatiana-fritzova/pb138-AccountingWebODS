<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <c:choose>
            <c:when test="${not empty noowner}">
                <div class="alert alert-danger alert-dismissible">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>Failure!</strong> No information about owner. Set it <a href="myProfile/">here</a>, please.
                </div>
            </c:when>
            <c:when test="${not empty failure}">
                <div class="alert alert-danger alert-dismissible">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>Failure!</strong> <c:out value="${failure}"/>
                </div>
            </c:when>
            <c:when test="${not empty success}">
                <div class="alert alert-success alert-dismissible">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>Success!</strong> Added new invoice.
                </div>
            </c:when>
        </c:choose>

        <h1>New invoice</h1><br>
      
        <form action="${pageContext.request.contextPath}/newInvoice" method="post">

            <div id="itemInputs">
                <input ng-repeat="x in products" name="itemName" value="{{x[0]}}" hidden>
                <input ng-repeat="x in products" name="{{x[0]}}Price" value="{{x[1]}}" hidden>
                <input ng-repeat="x in products" name="{{x[0]}}Pieces" value="{{x[2]}}" hidden>
            </div>
            <input type="number" name="total" value="{{sum}}" type="number" hidden>

            <div class="row">
                <div class="col-xs-12">
                    <button type="submit" class="btn btn-primary btn-md">
                        <div id="create">Save</div>
                    </button>
                </div>
            </div>

            <br>
            <div class="row">
                <div class="col-md-6">
                    <label class="control-label" for="sel1">Invoice type:</label>
                    <select class="form-control" id="sel1" name="type">
                        <option value="expense">Expense</option>
                        <option value="income">Income</option>
                    </select>
                </div>
                <div class="form-group col-md-3">
                    <label class="control-label">Issue date:</label>
                    <input type="date" class="form-control" name="issueDate" required/>
                </div>
                <div class="form-group col-md-3">
                    <label class="control-label">Due date:</label>
                    <input type="date" class="form-control" name="dueDate" required>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-6">
                    <label class="control-label">Name:</label>
                    <input type="text" class="form-control" name="fromToPerson" required autofocus/>
                </div>
                <div class="form-group col-md-6">
                    <label class="control-label">Address:</label>
                    <input type="text" class="form-control" name="addressPerson" required/>
                </div>
            </div>
        </form>
        <div class="panel panel-default">
            <div class="panel-heading">Items</div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="input-group">
                            <span class="input-group-addon glyphicon glyphicon-pencil" aria-hidden="true"></span>
                            <input ng-model="item" type="text" id="description" class="form-control" name="itemDescription" placeholder="Description">
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <div class="input-group">
                            <span class="input-group-addon glyphicon glyphicon-euro" aria-hidden="true"></span>
                            <input ng-model="price" type="number" id="price" class="form-control" name="itemPrice" placeholder="Price"
                                   min="0" step="0.01">
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <div class="input-group">
                            <span class="input-group-addon glyphicon glyphicon-resize-vertical"></span>
                            <input ng-model="pieces" type="number" id="pieces" class="form-control" name="itemPrice" min="0" placeholder="Pieces">
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <button ng-click="addItem()" type="button" class="btn btn-info" >
                            <i class="material-icons">add</i>
                        </button>
                    </div>
                </div>
                <br>

                <div class="row">
                    <div class="col-sm-12">
                        <div class="table-responsive" id="tableOfItems"></div>
                        <table class="table table-hover">
                            <thead>
                            <th class="col-md-6">Description</th>
                            <th class="col-md-2">Price</th>
                            <th class="col-md-2">Pieces</th>
                            <th class="col-md-2">Remove</th>
                            </thead>
                            <tbody>
                            <tr ng-repeat="x in products">
                                <td class="col-md-6">{{x[0]}}</td>
                                <td class="col-md-2">{{x[1]}} €</td>
                                <td class="col-md-2">{{x[2]}}</td>
                                <td class="col-md-2"><span ng-click="removeItem($index)" class="glyphicon glyphicon-minus-sign"></span></td>
                            </tr>
                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
            <div class="panel-footer">Total: {{sum}} €</div>
        </div>

    </div>

</div>

<script src = "https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script>
    var app = angular.module("itemList", []);
    app.controller("myCtrl", function($scope) {
        $scope.products = [];
        $scope.names = [];
        $scope.sum = 0;
        $scope.addItem = function () {
            document.getElementById("tableOfItems").style.display = "block";
            if (!$scope.item || !$scope.price || !$scope.pieces) {
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
