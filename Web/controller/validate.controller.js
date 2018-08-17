'use strict';

var Web3 = require('web3'),
  web3 = new Web3('http://localhost:8102'),
  mysql = require('mysql'),
  fs = require('fs'),
  time = require('../tool/time'),
  jwt = require('jwt-simple'),
  config = require('../config'),
  conn = mysql.createConnection(config.development_database),
  msg = require('../tool/msg');

exports.validateToken = function (req, res, callback) {
  var accessToken = (req.body && req.body.accessToken) || (req.query && req.query.accessToken) || req.headers['x-access-token'],
    decodedData,
    query

  try {
    decodedData = jwt.decode(accessToken, config.secret);
  } catch(e) {
    msg.sendMsg(res, 400, {
      message: 'Token Decoding Error'
    });
    return;
  }

  if (decodedData.exp < time.getTimestamp(new Date())) {
    msg.sendMsg(res, 201, {
      message: 'Accesstoken Expired'
    });
    return;
  }

  query = 'SELECT accessToken FROM User WHERE userId="' +
    decodedData.userId + '"'
  conn.query(query, function (error, results) {
    if (error) {
      msg.sendMsg(res, 400, {
        error: error
      });
    } else {
      if (results[0].accessToken === accessToken) {
        req.body.userId = decodedData.userId;
        callback();
      } else {
        msg.sendMsg(res, 401, {
          message: 'Invalid user'
        });
      }
    }
  });
};

exports.validateKeystore = function (req, res, callback) {
  var keystore = req.body.keystore,
    userId = req.body.userId,
    password = req.body.password,
    query,
    account,
    b;


  if (keystore && password) {
    b = new Buffer(req.body.keystore, 'base64');
    keystore = b.toString();

    try {
      account = web3.eth.accounts.decrypt(keystore, password);
    } catch (e) {
      console.log(e);
      msg.sendMsg(res, 400, {
        error: String(e)
      });
      return;
    }
    callback(account);
  } else {
    msg.sendMsg(res, 400);
  }
};