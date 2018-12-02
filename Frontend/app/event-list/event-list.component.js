'use strict';

angular.
  module('eventList').
  component('eventList', {
    templateUrl: 'event-list/event-list.template.html',
    controller: ['$scope', function EventListController($scope) {
        $scope.listPage = 'List';
      }]
  });