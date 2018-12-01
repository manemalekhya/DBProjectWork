'use strict';

angular.
  module('eventList').
  component('eventList', {
    templateUrl: 'event-list/event-list.template.html',
    controller: function EventListController() {
        this.listPage = 'List';
      }
  });