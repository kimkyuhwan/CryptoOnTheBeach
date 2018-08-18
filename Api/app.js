var express = require('express'),
  app = express(),
  bodyParser = require('body-parser'),
  PORT = process.env.PORT || 9000,
  parseFormdata = require('parse-formdata'),
  apiRoutes = express.Router(),
  blockchainAPI = require('./routes/blockchain_api');
  apiController = require('./controller/blockchain_api');

app.use(bodyParser.json({
  extended: true
}));
// app.use(bodyParser.urlencoded({ extended: true }))

app.all('/*', function (req, res, next) {
  // CORS headers
  res.header('Access-Control-Allow-Origin', '*'); // restrict it to the required domain
  res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE,OPTIONS');
  
  if (req.method === 'OPTIONS') {
    res.status(200).end();
  } else {
    next();
  }
});

// app.all('blockchain-api/*', );
app.use('/blockchain-api', blockchainAPI);

app.listen(PORT, function () {
  console.log('server is now started');
  console.log('port:' + PORT);
})
