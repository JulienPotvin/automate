angular
  .module('automate.dashboard')
  .directive('parkings', function() {
    return {
      restrict: 'E',
      templateUrl: '/javascripts/parking/parking.html',
      replace: true,
      controller: 'ParkingCtrl',
      scope: {},
      controllerAs: 'vm',
      bindToController: true,
      link: function(scope, element, attrs, ctrl) {
        ctrl.init();
      }
    };
  })
  .controller('ParkingCtrl', function(mapService) {
    var vm = this;

    vm.init = function() {
      vm.mapService = mapService;

      mapService.getParkingList().then(function(parkings) {
        vm.parkings = parkings;
      });
    };
  });
