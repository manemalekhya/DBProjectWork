'use strict';

angular.
  module('eventDetail').
  component('eventDetail', {
    templateUrl: 'event-detail/event-detail.template.html',
    controller: function EventDetailController() {
        this.detailPage = 'Detail';
      }
  });