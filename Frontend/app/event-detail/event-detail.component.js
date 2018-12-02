'use strict';

angular.
  module('eventDetail').
  component('eventDetail', {
    templateUrl: 'event-detail/event-detail.template.html',
    controller: ['$scope', function EventDetailController($scope) {
        $scope.detailPage = 'Detail';
        $scope.eventName = 'Superbowl: 2018';
    }]
  });