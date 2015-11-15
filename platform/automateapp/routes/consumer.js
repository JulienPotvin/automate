var express = require('express');
var router = express.Router();

//var parking = require('parking');


//GET
// input => {
//            userLatitude:Double,
//            userLongitude:Double,
//            googleDestinationQuery: String,
//            expectedParkingDuration:Long
//          }
// output => [
//             {
//               parkingId: UUID,
//               parkingLocation: {
//                 lat: Double,
//                 long: Double
//               },
//               available: Boolean,
//               pricingInfo: {
//                 price: Double,
//                 ttl: Long ,
//                 specialOffer: String
//               }
//             },
//               ...
//           ]

var fetchStates = function(lat,lon){

}

var fetchDiscounts = function(ids){

}

var fetchRestrictions = function(ids){

}



router.get('/listNearbyParkings', function(req, res) {




  res.json(200);
});




module.exports = router;
