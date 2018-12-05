'use strict';

angular.
  module('ticketApp').
  config(['$routeProvider',
    function config($routeProvider) {
      $routeProvider.
        when('/:userId/', {
          template: '<event-search></event-search>'
        }).
        when('/:userId/events/list/:search', {
          template: '<event-list></event-list>'
        }).
        when('/:userId/events/list/lat/:lat/lng/:lng', {
          template: '<event-list></event-list>'
        }).
        when('/:userId/events/map/:cityId', {
          template: '<city-map></city-map>'
        }).
        when('/:userId/events/detail/:eventId', {
          template: '<event-detail></event-detail>'  
        }).
        when('/:userId/events/detail/:eventId/success', {
          template: '<thank-you></thank-you>'
        }).
        otherwise('/:userId/');
    }
  ]);


