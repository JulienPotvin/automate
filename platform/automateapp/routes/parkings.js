var express = require('express');
var router = express.Router();

var toSpot = function(hit){
  var root = hit.get("_source")
  var id = root.get("spot_id")
  var latLong = root.get("spot_latlon")
  var latitude = latLong.get(0)
  var longitude = latLong.get(1)
  return {
            parkingId: id,
            parkingLocation: {
                lat  :latitude,
                long :longitude
            }
         }
}
var toSpots = function(raw){
  return raw
           .get("hits")
           .get("hits")
           .map(toSpot);
}
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
    var allRawSpots = es.get(
      "elastic search query here",//TODO: use real query
      function(error,response){
        if(error){
          console.log('oooo nooo! '+error)
        } else {
          res.json(toSpots(response))
        }
      });
})

/*
 * POST state
 input => {
            parkingId:UUID,
            state:Boolean
          }
 output => {}.
 */
router.post('/state', function(req, res) {
    //TODO : UPDATE parkingSpotIndex
    res.send(200);
});


module.exports = router;
