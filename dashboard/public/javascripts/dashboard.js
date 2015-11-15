angular
  .module('automate.dashboard', [
    'ui.bootstrap.datetimepicker'
  ])
  .run(function($interval, mapService) {
    $interval(function() {
      mapService.getParkingList(true);
    }, 2000);
  })
  .value('google', window.google)
  .value('moment', window.moment);
