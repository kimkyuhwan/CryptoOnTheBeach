'use strict';

var Web3 = require('Web3'),
	web3 = new Web3('http://localhost:8102'),
	async = require('async'),
	mysql = require('mysql'),
	jwt = require('jwt-simple'),
	config = require('../config'),
	conn = mysql.createConnection(config.development_database),
	msg = require('../tool/msg'),
	time = require('../tool/time');

// sql.setDialect('mysql');

exports.signUp = function (req, res) {
	var userId = req.body.userId,
		password = req.body.password,
		address = req.body.address,
		accountInfo,
		authData,
		keystore,
		query;

	if (userId && password && address) {
		accountInfo = web3.eth.accounts.create(password);
		authData = generateToken(userId);

		query = 'INSERT INTO User'
			+ '(userId, password, address, ethAddress, accesstoken)'
			+ 'VALUES('
			+ '"' + userId + '"'
			+ ',"' + password + '"'
			+ ',"' + address + '"'
			+ ',"' + accountInfo.address + '"'
			+ ',"' + authData.accesstoken + '"'
			+ ')';
		
		conn.query(query, function (error) {
			if (error) {
				msg.sendMsg(res, 400, {
					error: error
				});
				return;
			}
			keystore = web3.eth.accounts.encrypt(accountInfo.privateKey, password);
			msg.sendMsg(res, 201, {
				data: {
					keystore: keystore,
					accesstoken: authData.accesstoken
				}
			});
		});
	}
};

exports.signIn = function (req, res) {
	var userId = req.body.userId,
		password = req.body.password,
		query,
		authData;

	if (userId && password) {
		async.waterfall([
			function (callback) {
				query = 'SELECT password, accesstoken FROM User'
				+ ' WHERE userId="'
				+ userId + '"';
				
				conn.query(query, function (error, results) {
					if (error) {
						callback(error);
					} else if (password === results[0].password) {
						callback();
					} else {
						msg.sendMsg(res, 401, {
							message: 'User Not Found'
						});
					}
				});
			},
			function (callback) {
				authData = generateToken(password);
				query = 'UPDATE User SET accesstoken='
					+ '"' + authData.accesstoken + '" '
					+ 'WHERE userId='
					+ '"' + userId + '"';

				conn.query(query, function (error) {
					if (error) {
						callback(error);
					} else {
						msg.sendMsg(res, 200, {
							data: {
								accesstoken: authData.accesstoken
							}
						});
					}
				});
			}
			], function (error) {
				if (error) {
					console.log(error);
					msg.sendMsg(res, 400, {
						error: error
					});
				}
			});
	} else {
		msg.sendMsg(res, 400);
	}
};

exports.isUserIdDuplicated = function (req, res) {
	var query,
		userId = req.query.userId;

	if (userId) {
		query = 'SELECT userId FROM User'
			+ ' WHERE userId="'
			+ userId + '"';

		conn.query(query, function (error, results) {
			if (error) {
				msg.sendMsg(res, 500, {
					error: error
				});
				return;
			}
			if (results.length < 1) {
				msg.sendMsg(res, 200, {
					message: 'User Not Found'
				});
			} else {
				msg.sendMsg(res, 200, {
					message: 'User Already Exists'
				});
			}
		})
	}
};

function generateToken (userId) {
  var accessexpire = expiresIn(config.accessExpiresInDay),
    date = new Date(),
    accesstoken = jwt.encode({
      userId: userId,
      type: 'accesstoken',
      exp: accessexpire,
      timestamp: date.getTime()
    }, config.secret);

  return {
    userId: userId,
    accesstoken: accesstoken,
    accessexpire: accessexpire
  };
}

function expiresIn (numDays) {
  var now = new Date(),
    date = new Date(now.getFullYear(), now.getMonth(), now.getDate() + numDays);

  return time.getTimestamp(date);
}


