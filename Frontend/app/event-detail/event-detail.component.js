'use strict';

angular.
  module('eventDetail').
  component('eventDetail', {
    templateUrl: 'event-detail/event-detail.template.html',
    controller: ['$scope', '$routeParams', '$http', '$location', function EventDetailController($scope, $routeParams, $http, $location) {
        $scope.userId = $routeParams.userId;
        $scope.eventId = $routeParams.eventId;

        $scope.initEventDetails = function(){
          $scope.getData();
          $scope.isValidCheckout = false;
	        /*$scope.event = {
		        id: 1,
            seatid: 1,
		        name: "Wrestlemania",
		        team1:{id:1,name:'John Cena'},
		        team2:{id:2,name:'Kane'},
		        stadium:{name:'PPG Paints Arena',latitude:1.1,longitude:1.2,location:'123 Street',city:'Pittsburgh',state:'PA',zip:"15206"},
		        event_date:Date.now()
	        };*/
	        $scope.seatTypeArr = [
            { 
              "type" : "Regular",
              "value" : "regular"
            },
            { 
              "type" : "VIP", 
              "value" : "vip"
            }];

	        
          
        };
  	    // END of init function
  
        $scope.checkout = function(){
          if($scope.ticket.typeOfSeat != undefined 
            && 
            ($scope.ticket.noOfAdults > 0 || $scope.ticket.noOfChildren > 0)){
            $scope.isValidCheckout = true;
          }
          else{
            alert("Please enter proper parameters");
          }
        };

        // $scope.initCheckoutPage = function(){
        //   $scope.booking =
        //   { 
        //     "event" : 1,
        //     "noOfAdults" : 4,
        //     "noOfChildren" : 5,
        //     "typeOfSeat" :'VIP'
        //   };
        //   // $scope.cardMonth="";
        //   // $scope.cardCvv="";
        //   // $scope.cardYear="";
        //   // $scope.cardNumber="";
        // };
        // END of init function

        $scope.pay = async function(){
            console.log($scope.cardNumber);
            console.log($scope.cardMonth);
            console.log($scope.cardYear);
            console.log($scope.cardCvv);
            if($scope.cardNumber && $scope.cardMonth && $scope.cardYear && $scope.cardCvv && $scope.cardMonth<13 && $scope.cardMonth>0 && $scope.cardYear< 35 && $scope.cardYear>17 && $scope.cardCvv >=1 && $scope.cardCvv.length<=999){
                if(JSON.stringify($scope.cardNumber).length==16){
                  alert("Okay");
                  //http
                  $location.path('/home');
                }
                else
                  alert("Invalid card number");
            }else if($scope.cardNumber){
                if(JSON.stringify($scope.cardNumber).length==10){
                    alert("Okay");
                    $location.path();
                }
                else
                    alert("Invalid Account Details");
            }
            else{
                alert("Errors!");
            }
        };

        $scope.getEventId = function(){
          return $routeParams.eventId;
        }

        $scope.getData = async function(eventId){
          console.log("getData");
          console.log(eventId);
          /*var query = await $scope.buildQuery();
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

          });*/
        };

        $scope.getData = async function(){
          var eventId = await $scope.getEventId();
          
          await $http({
                method: 'GET',
                url: 'http://localhost:8080/eventdetail?eid='+ eventId
              }).then(function successCallback(response) {
                    $scope.event = response.data;
                    //console.log(response.data);
                }, function errorCallback(response) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    console.log(response);
                    alert('Oops something went wrong! Please try in sometime!');

              });
          };

          $scope.print = function(){
            console.log($scope.ticket.typeOfSeat);
          };


    }]
  });