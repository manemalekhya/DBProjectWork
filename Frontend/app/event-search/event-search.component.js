'use strict';

angular.
  module('eventSearch').
  component('eventSearch', {
    templateUrl: 'event-search/event-search.template.html',/*,
    resolve: {
        message: "Hello"
    },*/
    controller: ['$scope', 'City', 'Sport', /*'message',*/ function EventSearchController($scope, City, Sport/*, message*/) {
        // $scope.searchPage = message;
        $scope.searchPage = "message";
        $scope.cities = City.query();
        $scope.sports = Sport.query();

        //search function
        $scope.searchEvent = function() {
        	$scope.searchText = "Yellow!";
        	console.log("Hello");
    	};
        //search city function

        $scope.selectCity = function(cityName){
          // alert(cityName);
          window.location.replace("file:///C:/Users/Varun%20Nair/git/DBProjectWork/Frontend/app/src/city_details.html?"+cityName)
        };
        //search sport
        

        //querying backend
        /*return $http({
              method: 'GET',
              url: API_BASE + 'campaignGroup/' + campaignGroupId + '/campaignGroupBrowserNotificationSettings'
            })
            .then(function(response) {
              return response.data.data;
            });

        return $http({
          cache: false,
          method: 'GET',
          url: (pathname === "/" ? ENGAGE_BASE : ENGAGE_ORIGIN + pathname + "-/v1/") + 'logged-in-users',
          params: { bluffIE: Math.random() }
        });*/
      }]
  });