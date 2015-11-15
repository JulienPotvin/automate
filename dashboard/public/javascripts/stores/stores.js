angular
  .module('automate.dashboard')
  .directive('discount', function() {
    return {
      restrict: 'E',
      templateUrl: '/javascripts/stores/discount.html',
      replace: true,
      controller: 'DiscountCtrl',
      scope: {},
      controllerAs: 'vm',
      bindToController: true,
      link: function(scope, element, attrs, ctrl) {
        ctrl.init();
      }
    };
  })
  .controller('DiscountCtrl', function() {
    var vm = this;

    vm.code = 'X45M345';
    vm.message = 'Spend $50 of more and we will pay for 2h parking';

    vm.init = function() {};
  });
