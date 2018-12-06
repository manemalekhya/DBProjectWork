'use strict';

angular.
  module('editEvents').
  component('editEvents', {
    templateUrl: 'edit-events/edit-events.template.html',
    controller: ['$scope', 'User','$http','$routeParams', function EditEventDetailController($scope, User,$http,$routeParams) {
        //$scope.detailPage = 'Detail';
        //$scope.eventName = 'Superbowl: 2018';
        // console.log();
        $scope.eventId=$routeParams.eventId || 0;
        $scope.userId = User.getId();
        
        $scope.initEditEventDetails = async function(){
          // $scope.event = {
		       //  id: 1,
         //    seatid: 1,
		       //  name: "Wrestlemania",
		       //  team1:{id:1,name:'John Cena'},
		       //  team2:{id:2,name:'Kane'},
		       //  stadium:{name:'PPG Paints Arena',latitude:1.1,longitude:1.2,location:'123 Street',city:'Pittsburgh',state:'PA',zip:"15206"},
		       //  event_date:Date.now()
	        // };
          $scope.event={};

          if($scope.eventId){
            await $http({
              method: 'GET',
              url: 'http://localhost:8080/eventdetail?eid='+$scope.eventId 
            }).then(function successCallback(response) {
                console.log(response.data);
                  // $scope.events_list = response.data;
                  $scope.event= response.data;
                  $scope.event.edate=new Date($scope.event.edate);
                  $scope.event.id=$scope.eventId
                  console.log($scope.event);
              }, function errorCallback(response) {
                  // called asynchronously if an error occurs
                  // or server returns response with an error status.
                  console.log(response);
                  alert('Oops something went wrong! Please try in sometime!');

            });  
          }
  
        $scope.updateEvent = async function(){
          if(true){
            await $http({
              method: 'GET',
              url: 'http://localhost:8080/editEvent?eid='+$scope.event.id+'&name='+$scope.event.ename
              +'&locid='+$scope.event.locid+'&edate='+$scope.event.edate.toLocaleDateString("en-US")+'&t1id='+$scope.event.t1id
              +'&t2id='+$scope.event.t2id+'&status='+$scope.event.status+'&res='+$scope.event.result 
            }).then(function successCallback(response) {
                console.log(response.data);
                  // $scope.events_list = response.data;
              }, function errorCallback(response) {
                  console.log(response);
                  alert('Oops something went wrong! Please try in sometime!');

            });
          }
        };
}
    }]
  });