var express = require('express');
var router = express.Router();

/*
 * GET userlist.
 */
router.get('/userlist', function(req, res) {
    var db = req.db;
    var collection = db.get('userlist');
    collection.find({},{},function(e,docs){
        res.json(docs);
    });
});

router.get('/slot/:spotid', function(req, res) {
    var es = req.es;

    var collection = es.get({
	  index: 'automate',
	  type: 'spots',
	  id: req.params.spotid
	}, function (error, response) {
	  return res.json(response);
	});
});




module.exports = router;
