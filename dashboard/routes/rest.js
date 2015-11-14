var express = require('express');
var router = express.Router();

router.get('/parking/list', function(req, res) {
  res.json([
    {
      parkingId: 1,
      parkingLocation: {
        lat: 45.5255694,
        long: -73.5948246
      }
    },
    {
      parkingId: 2,
      parkingLocation: {
        lat: 45.52585,
        long: -73.5953
      }
    },
    {
      parkingId: 3,
      parkingLocation: {
        lat: 45.52625,
        long: -73.5962
      }
    }
  ]);
});

module.exports = router;
