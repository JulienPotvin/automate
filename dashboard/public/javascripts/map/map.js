angular
  .module('automate.dashboard')
  .directive('map', function() {
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
  .controller('MapCtrl', function($timeout, google, mapService) {
    var vm = this;

    vm.init = function() {
      var lagare = new google.maps.LatLng(45.5255694, -73.5948246);

      vm.map = new google.maps.Map(document.getElementById('map'), {
        center: lagare,
        zoom: 17
      });

      mapService.getParkingList().then(function(parkings) {
        parkings.forEach(function(p) {
          var marker = new google.maps.Marker({
            position: {
              lat: p.parkingLocation.lat,
              lng: p.parkingLocation.long,
            },
            icon: '/images/markers/default.png',
            map: vm.map,
            title: p.parkingId.toString()
          });
          marker.addListener('click', vm.toggleMarkerSelection);
        });
      });
    };

    vm.toggleMarkerSelection = function() {
      var selected = mapService.selected;
      var index = selected.indexOf(this);

      if (index === -1) {
        selected.push(this);
        this.setIcon('/images/markers/selected.png');
      } else {
        selected.splice(index, 1);
        this.setIcon('/images/markers/default.png');
      }
    };
  })
  .service('mapService', function($http) {
    this.getParkingList = function() {
      var self = this;

      return $http.get('/rest/parking/list').then(function(response) {
        self.parkings = response.data;
        self.selected = [];
        return self.parkings;
      });
    };
  });
