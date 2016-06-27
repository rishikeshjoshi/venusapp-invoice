'use strict';

/**
 * @ngdoc function
 * @name ngInvoiceApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the ngInvoiceApp
 */
angular.module('ngInvoiceApp')
  .controller('AboutCtrl', function ($scope,$routeParams) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    $scope.invoiceId = $routeParams.invoiceId;
  });
