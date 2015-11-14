angular
  .module('automate.dashboard')
  .directive('map', function(google) {
    return {
      restrict: 'E',
      templateUrl: '/javascripts/map/map.html',
      replace: true,
      controller: 'MapCtrl',
      scope: {},
      controllerAs: 'vm',
      bindToController: true,
      link: function(scope, element, attrs, ctrl) {
        ctrl.init();
      }
    };
  })
  .controller('MapCtrl', function($timeout, google) {
    var vm = this;

    vm.init = function() {
      var lagare = new google.maps.LatLng(45.5255694, -73.5948246);

      var map = new google.maps.Map(document.getElementById('map'), {
        center: lagare,
        zoom: 17
      });

      console.log(map);
    };

  });
