'use strict';

/**
 * @ngdoc function
 * @name ngInvoiceApp.controller:NewInvoiceCtrl
 * @description
 * # MainCtrl
 * Controller of the ngInvoiceApp
 */
angular.module('ngInvoiceApp')
  .controller('NewInvoiceCtrl', function ($scope,$http) {

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

          });

    };
}
});
