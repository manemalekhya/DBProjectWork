'use strict';

angular.
  module('eventSearch').
  component('eventSearch', {
    templateUrl: 'event-search/event-search.template.html',
    controller: function EventSearchController() {
        this.searchPage = 'List';
      }
  });