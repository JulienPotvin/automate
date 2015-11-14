var express = require('express');
var router = express.Router();

/*
 * POST discount
   {
    sponsorId:UUID,
    parkingIds: Set[UUID],
    discountRate: Double,
    timerange: Set[ Interval ]
  }.
 */
router.post('/discount', function(req, res) {
  var es = req.es;
  var body = req.body

  var sponsorId = body.sponsorId;
  var parkingsIds = body.parkingIds;
  var rate = body.discountRate;
  var timeranges = body.timerange;
  console.log(sponsorId,parkingsIds,rate,timeranges);
  //TODO : UPDATE parkingSpotIndex
  res.sendStatus(200)
});



module.exports = router;
