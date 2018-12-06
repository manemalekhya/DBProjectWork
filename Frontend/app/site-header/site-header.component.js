'use strict';

angular.
  module('siteHeader').
  component('siteHeader', {
    templateUrl: 'site-header/site-header.template.html',
    controller: ['$scope', '$routeParams', function SiteHeaderController($scope, $routeParams) {
        this.siteHeader = 'Site Header';
        $scope.userId = $routeParams.userId;
        console.log("change");
      }]
  });