var express = require('express');
var router = express.Router();

//var parking = require('parking');
var fetchNearbyParkings = function(es,qlat,qlon,callback){
  var toParkings = function(raw){
    var toParking = function(hit){
      var root = hit._source;
      var parking = {
        id: root.spot_id ,
        lat: root.spot_latlon[1],
        lon: root.spot_latlon[0],
        basePrice: root.spot_base_price,
        availability: root.spot_availability
      };
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
      size: 12,
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

  //   query :{
  //       "aggs" : {
  //        "rings_around_me" : {
  //            "geo_distance" : {
  //                "field" : "spot_latlon",
  //                "unit":     "m",
  //                "origin" :
  //                  {
  //                    "lat": qlat,
  //                    "lon": qlon
  //                  },
  //                "ranges" : [
  //                    { "to" : 10 }
  //                ]
  //            }
  //        }
  //    },
  //    "post_filter": {
  //      "geo_distance": {
  //        "distance":   "10m",
  //        "spot_latlon": {
  //          "lat": qlat,
  //          "lon": qlon
  //        }
  //      }
  //    }
  //  }

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
router.get('/listNearbyParkings', function(req, res) {
  var es = req.es
  // var body = req.body

  // var google = body.googleDestinationQuery => lat, lon

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
