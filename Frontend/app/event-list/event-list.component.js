'use strict';

angular.
  module('eventList').
  component('eventList', {
    templateUrl: 'event-list/event-list.template.html',
    controller: ['$scope', '$routeParams', function EventListController($scope, $routeParams) {
    	
        //$scope.listPage = ServiceB.getValue();
        $scope.listPage = decodeURI($routeParams.search);
        console.log($scope.listPage);
      }]
  });