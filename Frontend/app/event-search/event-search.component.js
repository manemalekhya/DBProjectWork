'use strict';

angular.
  module('eventSearch').
  component('eventSearch', {
    templateUrl: 'event-search/event-search.template.html',
    controller: ['$scope', 'City', 'Sport', '$rootScope', '$location', '$routeParams',
    function EventSearchController($scope, City, Sport, $rootScope, $location, $routeParams) {
        $scope.cities = City.query();
        $scope.sports = Sport.query();
        $scope.userId = $routeParams.userId;
        console.log($scope.userId);

        $scope.searchEvent = function(searchText) {
        	console.log(searchText);
        	var path = '/' + $scope.userId + '/events/list/' + encodeURI(searchText);
        	console.log(path);
        	$location.path(path);
        };

        $scope.showAllEvents = function() {
          var allpath = '/' + $scope.userId +'/events/list/';
          console.log(allpath);
        	$location.path(allpath);
        };
      }]
  });