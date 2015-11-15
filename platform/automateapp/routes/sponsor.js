var express = require('express');
var router = express.Router();

/*
 * POST discount
   {
    parkingIds: Set[UUID],
    message: String
  }.
 */
router.post('/discount', function(req, res) {
  var es = req.es;
  var body = req.body

  var parkingIds = body.parkingIds;
  var discount = body.message;
  //TODO : UPDATE parkingSpotIndex

  var parkingId = parkingIds
  console.log(parkingId);
  console.log(discount);

  var updated = 0;


  parkingIds.forEach(function(parkingId) {
    es.update({
  	  index: 'automate',
  	  type: 'spots',
  	  id: parkingId ,
  	  body: {
  	    // put the partial document under the `doc` key
  	    doc: {
  	      'spot_discount': discount
  	    }
  	  }
  	}, function (error, response) {
  		if(error){
  		      console.log('oooo noon! ' + error);
      } else {
        updated += 1;

        if (updated === parkingIds.length) {
          res.sendStatus(200);
        }
      }
  	})

  });


});



module.exports = router;
