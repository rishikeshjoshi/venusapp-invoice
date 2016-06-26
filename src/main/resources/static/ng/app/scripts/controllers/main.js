'use strict';

/**
 * @ngdoc function
 * @name ngInvoiceApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the ngInvoiceApp
 */
angular.module('ngInvoiceApp')
  .controller('MainCtrl', function ($scope,$http) {


    $http.get('/api/invoices')
    .success(function(data, status, headers, config) {
      $scope.invoices = data;
    })
    .error(function(data, status, headers, config) {
      // log error
    });
});
