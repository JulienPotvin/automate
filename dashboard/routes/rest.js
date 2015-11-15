var express = require('express');
var lodash = require('lodash');

var router = express.Router();

var parkings = [
  {
    parkingId: 1,
    parkingLocation: {
      lat: 45.5255694,
      long: -73.5948246
    },
    available: true,
    pricingInfo: {
      basePrice: 2.75,
      surgePriceIncrease: 0.25
    }
  },
  {
    parkingId: 2,
    parkingLocation: {
      lat: 45.52585,
      long: -73.5953
    },
    available: true,
    pricingInfo: {
      basePrice: 2.75,
      surgePriceIncrease: 0.25
    }
  },
  {
    parkingId: 3,
    parkingLocation: {
      lat: 45.52625,
      long: -73.5962
    },
    available: true,
    pricingInfo: {
      basePrice: 2.75,
      surgePriceIncrease: 0.25
    }
  }
];

var firstTime = true;

router.get('/parking/list', function(req, res) {
  if (firstTime) {
    res.json(parkings);
    firstTime = false;
  } else {
    var index = Math.floor(Math.random() * parkings.length);
    var parking = parkings[index];

    parking.available = !parking.available;

    res.json(parkings);
  }
});

router.post('/parking/alert', function(req, res) {
  console.log(req.body);
  res.sendStatus(200);
});

module.exports = router;
