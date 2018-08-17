var express = require('express'),
  app = express(),
  bodyParser = require('body-parser'),
  PORT = process.env.PORT || 9000,
  apiRoutes = express.Router(),
  blockchainAPI = require('./routes/blockchain_api');
  apiController = require('./controller/blockchain_api');

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }))

app.all('/*', function (req, res, next) {
  // CORS headers
  res.header('Access-Control-Allow-Origin', '*') // restrict it to the required domain
  res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE,OPTIONS')
  // Set custom headers for CORS
  res.header('Access-Control-Allow-Headers', 'Content-type,Accept,X-Access-Token,X-Key')
  if (req.method === 'OPTIONS') {
    res.status(200).end();
  } else {
    next();
  }
});


app.use('/blockchain-api', blockchainAPI);

app.listen(PORT, function () {
  console.log('server is now started');
  console.log('port:' + PORT);
})
