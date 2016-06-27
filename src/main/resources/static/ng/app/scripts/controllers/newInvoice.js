'use strict';

/**
 * @ngdoc function
 * @name ngInvoiceApp.controller:NewInvoiceCtrl
 * @description
 * # MainCtrl
 * Controller of the ngInvoiceApp
 */
angular.module('ngInvoiceApp')
  .controller('NewInvoiceCtrl', function ($scope,$http,$location) {

    /**
    * Check is invoice form is valid to be submitted or not
    **/
    $scope.isValid = function() {
       if($scope.invoiceForm.partyName && $scope.invoiceForm.invoiceDate) {
          return true;
       } else {
          return false;
       }
    };

    //Submitting an invoice form.
    $scope.submitForm = function() {

        // check to make sure the form is completely valid
        if ($scope.invoiceForm.$valid) {

          var data = {
            partyName : $scope.invoiceForm.partyName,
            invoiceDate : $scope.invoiceForm.invoiceDate,
            dueDate : $scope.invoiceForm.dueDate,
          }

          $http.post("/api/invoices/draft/", data).success(function(data, status) {
             $scope.invoiceForm.success = true;
             $location.path("/edit/" + data.id);
          });

    };
  }
}).controller('AddInvoiceItemCtrl', function ($scope,$http,$location,$routeParams) {

    //Invoice Id
    $scope.invoiceId = $routeParams.invoiceId;

    $http.get('/api/invoices/' + $scope.invoiceId)
        .success(function(data, status, headers, config) {
          $scope.invoice = data;
          console.log("Invoice Items" + data.items);
        })
        .error(function(data, status, headers, config) {
          $scope.error = true;
          alert('There was something wrong. Could not retrieve information for the invoice.');
        });

    $scope.openAddItemForm = function() {
      $scope.showAddItemForm = true;
    }

    $scope.submitForm = function() {

      var data = {
                  description : $scope.addItemForm.description,
                  quantity : $scope.addItemForm.quantity,
                  unitPrice : $scope.addItemForm.unitPrice,
                }

                $http.post("/api/invoices/" + $scope.invoiceId + "/addItem/", data)
                .success(function(data, status) {
                    if(status == 200) {
                     $scope.addItemForm.success = true;
                     $scope.invoice = data;
                     $scope.items = data.items;
                     $scope.showAddItemForm = false;

                     //Clear the form.
                     $scope.addItemForm = {};

                    } else {
                      alert('There was something wrong.');
                    }
                });
    }
}).controller('ViewInvoiceCtrl', function ($scope,$http,$location,$routeParams) {

      //Invoice Id
      $scope.invoiceId = $routeParams.invoiceId;

      $http.get('/api/invoices/' + $scope.invoiceId)
          .success(function(data, status, headers, config) {
            $scope.invoice = data;
            console.log("Invoice Items" + data.items);
          })
          .error(function(data, status, headers, config) {
            $scope.error = true;
            alert('There was something wrong. Could not retrieve information for the invoice.');
          });
});
