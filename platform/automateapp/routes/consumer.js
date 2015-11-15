var express = require('express');
var router = express.Router();

//var parking = require('parking');
var fetchNearbyParkings = function(es,qlat,qlon,callback){
  var toParkings = function(raw){
    var toParking = function(hit){
      var root = hit._source;
      console.log('discount bitches');
      console.log(root.spot_discount);
      var parking = {
        parkingId: root.spot_id ,
        parkingLocation: {
          lat: root.spot_latlon[1],
          long: root.spot_latlon[0],
        }, 
        basePrice: root.spot_base_price,
        physicalAvailability: root.spot_availability,
        discount: root.spot_discount
      };
      console.log(parking);
      return parking;
    }

    var hits = raw.hits.hits;
    var parkings = hits.map(toParking);
    return parkings;
  }

  return es.search({
    index : 'automate',
    type: 'spots',
    body: {
      from: 0,
      size: 14,
      query: {
        filtered: {
                filter: {
                    geo_distance: {
                        distance: '500m',
                        spot_latlon: {
                            lat: qlat,
                            lon: qlon
                        }
                    }
                }
            }
        }
    }

  },function (error, response) {
     if(error) {
       console.log('Oooh noes' + error);
     } else {
       var parkings= toParkings(response);
       callback(parkings)
     }
   });

}

var fetchDiscounts = function(ids){

}

var fetchRestrictions = function(ids){

}

var fetchLatLon = function(destination){
  //TODO:
}


//GET
// input => {
//            userLatitude:Double,
//            userLongitude:Double
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
router.get('/listNearbyParkings', function(req, res) {
  var es = req.es

   fetchNearbyParkings(
     es,
     req.query.lat,
     req.query.lon,
     (parkings) => {
       res.json(parkings);
     }
   );
});




module.exports = router;
