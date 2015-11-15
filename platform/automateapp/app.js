var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

//Database
var elasticsearch = require('elasticsearch');

//Elasticsearch

var client = new elasticsearch.Client({
  host: 'dockerhost:9200',
  log: 'debug'
});

var routes = require('./routes/index');
// var users = require('./routes/users');
var parking = require('./routes/parking');
var sponsor = require('./routes/sponsor');
var consumer = require('./routes/consumer');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

var memstates;

var getStates = function() {
  var toStates = function(raw){
    var hits = raw.hits.hits
    return hits.map(function(hit){
      var root = hit._source;
      var latLong = root.spot_latlon;
      return {
          parkingId: root.spot_id,
          parkingLocation: {
              lat: latLong[1],
              long: latLong[0]
          },
          pricingInfo: {
            basePrice: 2.75,
            surgePriceIncrease: 0.25,
            declinePriceDecrease: 0.25
          },
          physicalAvailability: root.spot_availability
       };
    });
  };


  return client.search({
    index : 'automate',
    query : { match_all : {} }
  },function(error,response) {
    if(error) {
      console.log('oooo noon! ' + error);
    } else {
      memstates = toStates(response);
    }
  });
};

getStates();

//Make our db accessible to our router
app.use(function(req,res,next){
    req.es = client;
    req.states = memstates;
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Methods', 'POST, GET, PUT, OPTIONS, DELETE');
    res.header('Access-Control-Max-Age', '3600');
    res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');

    next();
});


app.use('/', routes);
app.use('/parking',parking);
app.use('/sponsor',sponsor);
app.use('/consumer',consumer);


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
  app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
});


module.exports = app;
