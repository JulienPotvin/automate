angular
  .module('automate.dashboard')
  .directive('scheduler', function() {
    return {
      restrict: 'E',
      templateUrl: '/javascripts/scheduler/scheduler.html',
      replace: true,
      controller: 'SchedulerCtrl',
      scope: {},
      controllerAs: 'vm',
      bindToController: true,
      link: function(scope, element, attrs, ctrl) {
        ctrl.init();
      }
    };
  })
  .controller('SchedulerCtrl', function(mapService, schedulerService) {
    var vm = this;

    vm.init = function() {
      vm.duration = 24;
      vm.date = new Date();
      vm.mapService = mapService;
    }

    vm.submit = function() {
      var selected = mapService.selected;
      var alert = {
        ids: [],
        unavailabityIntervals: []
      };

      selected.forEach(function(s) {
        alert.ids.push(s.title);
      });

      alert.unavailabityIntervals.push({
        date: vm.date,
        duration: vm.duration
      });

      schedulerService.submitAlert(alert);
    };
  })
  .service('schedulerService', function($http) {
    var self = this;

    this.submitAlert = function(alert) {
      $http.post('/rest/parking/alert', alert).then(function(response) {
        return response.data;
      });
    };
  });
