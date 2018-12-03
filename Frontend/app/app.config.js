'use strict';

angular.
  module('ticketApp').
  config(['$routeProvider',
    function config($routeProvider) {
      $routeProvider.
        when('/', {
          template: '<event-search></event-search>'
        }).
        when('/events/search/:search', {
          template: '<event-list></event-list>'
        }).
        when('/events/map/:cityId', {
          template: '<city-map></city-map>'
        }).
        when('/events/:eventId', {
          template: '<event-detail></event-detail>'  
        }).
        otherwise('/');
    }
  ]);

/*angular.
  module('ticketApp').
  config(['$stateProvider', '$urlRouterProvider',
    function config($stateProvider, $urlRouterProvider) {

      $urlRouterProvider.otherwise('/');

      $stateProvider.
        state('home', {
          url: '/',
          templateUrl: 'event-search/event-search.template.html'
        }).
        state('home.events', {
          url: '/events',
          templateUrl: 'event-list/event-list.template.html'
        }).
        state('home.map', {
          url: '/maps',
          templateUrl: 'city-map/city-map.template.html'
        }).
        state('home.events.detail', {
          url: '/events/:eventId',
          templateUrl: 'event-detail/event-detail.template.html'  
        });
    }
  ]);*/

