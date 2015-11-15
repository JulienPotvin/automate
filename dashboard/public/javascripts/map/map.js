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
  .controller('MapCtrl', function($rootScope, $scope, google, mapService) {
    var vm = this;

    vm.init = function() {
      var lagare = new google.maps.LatLng(45.5955694, -73.5948246);

      vm.map = new google.maps.Map(document.getElementById('map'), {
        center: lagare,
        zoom: 11
      });

      vm.mapService = mapService;

      mapService.getParkingList().then(function(parkings) {
        vm.refreshParkings(parkings);
      });

      $scope.$watch('vm.mapService.parkings', function(parkings) {
        vm.refreshParkings(parkings);
      });
    };

    vm.refreshParkings = function(parkings) {
      if (!parkings) { return; }

      parkings.forEach(function(p) {
        var marker = mapService.markers[p.parkingId];

        if (!marker) {
          marker = new google.maps.Marker({
            position: {
              lat: p.parkingLocation.lat,
              lng: p.parkingLocation.long,
            },
            map: vm.map,
            title: p.parkingId.toString()
          });
          marker.addListener('click', function() {
            vm.toggleMarkerSelection(p, marker);
          });
          mapService.markers[p.parkingId] = marker;
        }

        if (!mapService.selected[p.parkingId]) {
          mapService.setIconForMarker(p, marker);
        }
      });
    };

    vm.toggleMarkerSelection = function(parking, marker) {
      var selected = mapService.selected;
      var id = parking.parkingId;

      if (!selected[id]) {
        selected[id] = parking;
        marker.setIcon('/images/markers/blue.png');
      } else {
        delete selected[id];
        mapService.setIconForMarker(parking, marker);
      }

      $rootScope.$apply();
    };
  })
  .service('mapService', function($http, $q) {
    this.selected = {};
    this.markers = {};

    this.getParkingList = function(force) {
      var self = this;

      if (!self.parkings || force) {
        return $http.get('http://localhost:3001/parking/listStates').then(function(response) {
          self.parkings = response.data;
          return self.parkings;
        });
      } else {
        var deferred = $q.defer();

        deferred.resolve(self.parkings);

        return deferred.promise;
      }
    };

    this.getAvailableParkings = function() {
      return this.parkings.filter(function(p) {
        return p.physicalAvailability === true;
      })
    };

    this.isSurge = function() {
      var available = this.getAvailableParkings();

      if (!this.parkings.length) {
        return false;
      }

      return (available.length / this.parkings.length) <= 0.4;
    };

    this.isLow = function() {
      var available = this.getAvailableParkings();

      if (!this.parkings.length) {
        return false;
      }

      return (available.length / this.parkings.length) >= 0.6;
    };

    this.calculateParkingPrice = function(parking) {
      var price = parking.pricingInfo.basePrice;
      var delta = parking.pricingInfo.surgePriceIncrease;

      if (this.isSurge()) {
        price += delta;
      }

      return price;
    };

    this.setIconForMarker = function(parking, marker) {
      var color = parking.physicalAvailability ? 'green' : 'red';
      var marker = this.markers[parking.parkingId];

      if (marker) {
        marker.setIcon('/images/markers/' + color + '.png');
      }
    };
  });
