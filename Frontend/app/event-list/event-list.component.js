'use strict';

angular.
  module('eventList').
  component('eventList', {
    templateUrl: 'event-list/event-list.template.html',
    controller: ['$scope', '$routeParams', '$http', '$location', '$filter',
    function EventListController($scope, $routeParams, $http, $location, $filter) {
    	$scope.userId = $routeParams.userId;
        $scope.today = $filter('date')(new Date(), "yyyy-MM-dd");

        if($routeParams.lat != undefined ||  $routeParams.lng != undefined){
            
        }

        $scope.buildQuery = function(){
        	var result;
            
            var date = "";
            if($scope.filter.date){
                date = $filter('date')($scope.filter.date, "yyyy-MM-dd"); 
            }

        	if(!($routeParams.search || $scope.searchText || $scope.filter || $scope.filter.city || $scope.filter.stadium || $scope.filter.sport || date))

		    	result =  "";
		    else
		    	result = ($scope.searchText || $routeParams.search ) || "";
		    return  "q="+result+"&c="+$scope.filter.city+"&s="+$scope.filter.stadium+"&sp="+$scope.filter.sport+"&d="+date;
		};

		$scope.getData = async function(){
		    var query = await $scope.buildQuery();
            $scope.searchText=$scope.searchText || $routeParams.search;
		    console.log(query);
		    await $http({
            	method: 'GET',
            	url: 'http://localhost:8080/eventList?'+ query
            }).then(function successCallback(response) {
                	$scope.events_list = response.data;
            	}, function errorCallback(response) {
                	// called asynchronously if an error occurs
                	// or server returns response with an error status.
                	console.log(response);
                	alert('Oops something went wrong! Please try in sometime!');

            });
		};

		$scope.eventListInit = async function(){
		    $scope.filter={};
		    $scope.filter.query="";
		    $scope.filter.city="";
		    $scope.filter.stadium="";
		    $scope.filter.sport="";
            $scope.filter.date="";
		    $scope.getData();  
		};
    }]
  });