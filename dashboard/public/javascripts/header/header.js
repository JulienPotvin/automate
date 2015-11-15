angular.module('automate.dashboard').directive('header', function() {
  return {
    restrict: 'E',
    templateUrl: '/javascripts/header/header.html',
    replace: true,
    scope: {
      isStoreMode: '='
    },
    link: function() { }
  };
});
