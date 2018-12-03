'use strict';

angular.
  module('cityMap').
  component('cityMap', {
    templateUrl: 'city-map/city-map.template.html',
    controller: ['$scope', '$http', 
     function cityMapsController($scope, $http) {


        $scope.initCityMap = async function(){
            await $http({
              method: 'GET',
              url: 'http://localhost:8080/cityMap?q='+"Pittsburgh"
            }).then(function successCallback(response) {
                console.log('SUCCESS');
                $scope.stadiums = response.data;
              }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                console.log('FAILURE');
                console.log(response);
              });  
          };


        $scope.initMap= async function() {
        var e = document.getElementById('myApp');     
        var $scope = angular.element(e).scope();
        // alert($scope.stadiums);
        console.log(1);
        await $scope.initCityMap();
        console.log(2);
        var image = {
          url: 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png',
          // This marker is 20 pixels wide by 32 pixels high.
          size: new google.maps.Size(20, 32),
          // The origin for this image is (0, 0).
          origin: new google.maps.Point(0, 0),
          // The anchor for this image is the base of the flagpole at (0, 32).
          anchor: new google.maps.Point(0, 32)
        };

        var shape = {
          coords: [1, 1, 1, 20, 18, 20, 18, 1],
          type: 'poly'
        };


        // var ppgPaintsArena = {lat: 40.4396, lng: -79.9893};
        // var heinzField = {lat: 40.4468, lng: -80.0158};
        // var pncPark = {lat: 40.4469, lng: -80.0057};

        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 13,
          center: $scope.stadiums[0].coordinates
        });

        var i;
        var markers = new Array($scope.stadiums.length);
        for(i=0; i < $scope.stadiums.length; i++){
            markers[i]=new google.maps.Marker({position:$scope.stadiums[i].coordinates, map:map, title:$scope.stadiums[i].name,icon: image, shape: shape});
            console.log(markers[i]);
            var stadiumName=$scope.stadiums[i].name;
            markers[i].addListener('click', function(data) {
             $scope.selectStadium(data)
            });
        }

        map.addListener('center_changed', function() {
          // 3 seconds after the center of the map has changed, pan back to the
          // marker.
          window.setTimeout(function() {
            map.panTo(marker.getPosition());
          }, 3000);
        });
      }

        google.maps.event.addDomListener(window, 'load', $scope.initMap());

     }]
  });