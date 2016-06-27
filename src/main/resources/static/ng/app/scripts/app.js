'use strict';

/**
 * @ngdoc overview
 * @name ngInvoiceApp
 * @description
 * # ngInvoiceApp
 *
 * Main module of the application.
 */
angular
  .module('ngInvoiceApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        <!-- Since this is served from Spring boot change the templateUrl as /ng/app as this is the folder under the static folder which is served by Spring -->
        templateUrl: '/ng/app/views/list.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/new', {
         templateUrl: '/ng/app/views/new.html',
         controller: 'NewInvoiceCtrl',
         controllerAs: 'newInvoice'
      })
      .when('/edit/:invoiceId', {
         templateUrl: '/ng/app/views/addItem.html',
         controller: 'AddInvoiceItemCtrl',
         controllerAs: 'addItem'
      })
      .when('/view/:invoiceId', {
         templateUrl: '/ng/app/views/view.html',
         controller: 'ViewInvoiceCtrl',
         controllerAs: 'viewInvoice'
      })
      .when('/about', {
        templateUrl: '/ng/app/views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
