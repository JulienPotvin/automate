var express = require('express');
var router = express.Router();

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
    //TODO :
})


module.exports = router;
