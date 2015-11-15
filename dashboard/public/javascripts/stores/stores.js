angular
  .module('automate.dashboard')
  .directive('discount', function() {
    return {
      restrict: 'E',
      templateUrl: '/javascripts/stores/discount.html',
      replace: true,
      scope: true,
      link: function(scope) {
        scope.model.code = 'X45M345';
        scope.model.message = 'Spend $50 of more and we will pay for 2h parking';
      }
    };
  });
