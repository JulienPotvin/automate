var express = require('express');
var router = express.Router();

var toSpot = function(hit){
  var root = hit._source;
  var latLong = root.spot_latlon;
  return {
            parkingId: root.spot_id,
            parkingLocation: {
                lat  :latLong[0],
                long :latLong[1]
            }
         };
};

var toSpots = function(raw){
  return raw
           .hits
           .hits
           .map(toSpot);
};

/*
  GET
  input  => {}
  output => [
            {
              parkingId:UUID,
              parkingLocation: {
                lat:Double,
                long:Double
              }
            },
             ...
           ]
*/
router.get('/list', function(req, res){
    var es = req.es;
    es.search({
      index : 'automate',
      query: { match_all: {} }
    },function(error,response){
        if(error){
          console.log('oooo nooo! ' + error);
        } else {
          res.json(toSpots(response));
        }
    });
});

//FOR TEST ONLY

var toStates = function(raw){
  var hits = raw.hits.hits
  return hits.map(function(hit){
    var root = hit._source;
    var latLong = root.spot_latlon;
    return {
              parkingId: root.spot_id,
              parkingLocation: {
                  lat  :latLong[0],
                  long :latLong[1]
              },
              pricingInfo :{
                basePrice : 2.75,
                surgePriceIncrease: 0.25,
                declinePriceDecrease : 0.25
              },
              physicalAvailability: root.spot_availability
           };
  });


}

var magic = function(states){
  var magicIndex =  Math.floor(Math.random() * states.length);
  var state = states[magicIndex];
  state.physicalAvailability = !state.physicalAvailability;
}
router.get('/listStates', function(req, res){
  var es = req.es;
  es.search({
    index : 'automate',
    query : { match_all : {} }
  },function(error,response){
    if(error){
      console.log('oooo noon! ' + error);
    } else {
      var states =toStates(response)
      magic(states)
      res.json(states)
    }
  }
);

});

/*
 * POST state
 input => {
            parkingId:UUID,
            state:Boolean
          }
 output => {}.
 */
router.post('/state', function(req, res) {
  var es = req.es;
  var body = req.body;

  var parkingId = body.parkingId;
  var state = body.state;
  // TODO : UPDATE parkingSpotIndex
  res.sendStatus(200);
});

/*POST /parking/regulate

    input => {
               parkingIds: [UUID],
               unavailabilityIntervals: [Interval]
             }
    output=> {}
*/
router.post('/regulate', function(req, res) {
  var es = req.es
  var body = req.body;

  var ids = body.parkingIds
  var intervals = body.unavailabilityIntervals
  //TODO: UPDATE regulation index
  res.sendStatus(200)
});

module.exports = router;
