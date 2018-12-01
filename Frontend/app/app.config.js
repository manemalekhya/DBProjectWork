'use strict';

angular.
  module('ticketApp').
  config(['$routeProvider',
    function config($routeProvider) {
      $routeProvider.
        when('/', {
          template: '<event-search></event-search>'
        }).
        when('/events', {
          template: '<event-list></event-list>'
        }).
        when('/events/:eventId', {
          template: '<event-detail></event-detail>'
        }).
        otherwise('/');
    }
  ]);
