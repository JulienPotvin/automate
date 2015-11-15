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
  .controller('MapCtrl', function($rootScope, google, mapService) {
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
            icon: '/images/markers/grey.png',
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
        this.setIcon('/images/markers/red.png');
      } else {
        selected.splice(index, 1);
        this.setIcon('/images/markers/grey.png');
      }

      $rootScope.$apply();
    };
  })
  .service('mapService', function($http, $q) {
    this.selected = [];

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

      return (available.length / this.parkings.length) <= 0.4;
    };

    this.calculateParkingPrice = function(parking) {
      var price = parking.pricingInfo.basePrice;
      var delta = parking.pricingInfo.surgePriceIncrease;

      if (this.isSurge()) {
        price += delta;
      }

      return price;
    };
  });
