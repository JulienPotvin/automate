angular
  .module('automate.dashboard', [
    'ui.bootstrap.datetimepicker'
  ])
  .value('google', window.google)
  .value('moment', window.moment);
