angular
  .module('automate.dashboard')
  .directive('alertSnow', function() {
    return {
      restrict: 'E',
      templateUrl: '/javascripts/alert/alert.html',
      replace: true,
      controller: 'AlertCtrl',
      scope: {},
      controllerAs: 'vm',
      bindToController: true,
      link: function(scope, element, attrs, ctrl) {
        ctrl.init();
      }
    };
  })
  .controller('AlertCtrl', function($rootScope, $element) {
    var vm = this;

    vm.init = function() {
      $rootScope.$on('automate.alert.snow', vm.showAlert);
    };

    vm.showAlert = function() {
      $element.css('display', 'block');
    };

    vm.closeAlert = function() {
      $element.css('display', 'none');
    };
  });
