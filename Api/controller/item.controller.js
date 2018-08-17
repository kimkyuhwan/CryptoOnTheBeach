'use strict';

var Tx = require('ethereumjs-tx'),
	Web3 = require('web3'),
	web3 = new Web3('http://localhost:8102'),
	mysql = require('mysql'),
	save = require('save-file'),
	fs = require('fs'),
	config = require('../config'),
	secondhandMarket = new web3.eth.Contract(config.secondhandMarket.interface, config.secondhandMarket.address),
	conn = mysql.createConnection(config.development_database),
	async = require('async'),
	msg = require('../tool/msg');

exports.registerItem = function (account, req, res, callback) {
	var userId = req.body.userId,
		name = req.body.name,
		subname = req.body.subname,
		timestamp = req.body.timestamp,
		purchased = req.body.purchased,
		images = req.body.images,
		price = req.body.price,
		extension = getExtension(req.body.extension),
		type = req.body.type,
		sn = req.body.sn,
		IMAGE_PATH = '../images/',
		imagesUrl,
		query,
		rawTx,
		tx,
		privateKey;

	console.log(web3.utils.hexToBytes(web3.utils.toHex(sn)));

	if (name && userId && timestamp && images && extension && sn) {
		imagesUrl = IMAGE_PATH + userId + '/' + String(timestamp) + '.' + extension;

		query = 'INSERT INTO Item'
			+ '(userId, name, subname, price, timestamp, purchased, images_url, extension)'
			+ 'VALUES('
			+ '"' + userId + '"'
			+ ',"' + name + '"'
			+ ',"' + subname + '"'
			+ ',' + price
			+ ',"' + timestamp + '"'
			+ ',"' + purchased + '"'
			+ ',"' + imagesUrl.slice(1) + '"'
			+ ',"' + extension + '"'
			+ ')';
		
		async.waterfall([
			function (callback) {
				conn.query(query, function (error) {
					if (error) {
						callback(error);
						return;
					}
					save(images, imagesUrl, function (error) {
						callback(error);
					});
				});
			},
			function (callback) {
				console.log('sn', sn);
				// sn = web3.utils.hexToBytes((web3.utils.toHex(sn)));
				sn = web3.utils.toHex(sn);
				web3.eth.getTransactionCount(account.address, function (error, nonce) {
					if (error) {
						callback(error);
						return;
					}
					rawTx = {
						nonce: nonce,
						gasLimit: config.gasLimit,
						to: config.secondhandMarket.address,
						value: 0x0,
						data: secondhandMarket.methods.registerProduct(sn).encodeABI()
					};

					privateKey = new Buffer(account.privateKey.slice(2), 'hex');
					tx = new Tx(rawTx);
					tx.sign(privateKey);
					callback(error, tx.serialize());
				})
			},
			function (serializedTx, callback) {
				web3.eth.sendSignedTransaction('0x' + serializedTx.toString('hex'))
				.on('error', function (error) {
					callback(error);
					// should delete item registered in database
				})
				.on('transactionHash', function (hash) {
					console.log(hash);
					msg.sendMsg(res, 200);
				})
				.on('receipt', function (data) {
					console.log(data);
					// blockchain save complete
				});
			}
			], function (error) {
				if (error) {
					console.log(error);
					msg.sendMsg(res, 400, {
						data: {
							error: error
						}
					});
				}
			});
	} else {
		msg.sendMsg(res, 400);
	}
};

exports.unregisterItem = function (req, res) {
	var userId = req.body.userId,
		itemId = req.body.itemId,
		query;

	if (userId && itemId) {
		async.waterfall([
			function (callback) {
				query = 'SELECT userId FROM Item '
					+ 'WHERE itemId=' + '"' + itemId + '"';
				conn.query(query, function (error, results) {
					if (error) {
						callback(error);
						return;
					}

					if (results[0].userId === userId) {
						callback(error);
					}
				});
			},
			function (callback) {
				query = 'DELETE From Item '
					+ 'WHERE itemId=' + '"' + itemId + '"';

				conn.query(query, function (error) {
					if (error) {
						callback(error);
					} else {
						msg.sendMsg(res, 200);
					}
				});
			}
		], function (error) {
			if (error) {
				msg.sendMsg(res, 400, {
					error: error
				});
			}
		});
	} else {
		msg.sendMsg(res, 400);
	}
};

exports.getItems = function (req, res) {
	var query;
	
	query = 'SELECT Item.*, User.ethAddress From Item INNER JOIN User ON Item.userId=User.userId';

	conn.query(query, function (error, results) {
		if (error) {
			console.log(error);
			msg.sendMsg(res, 500);
			return;
		}

		for (var i = 0; i < results.length; i++) {
			try { 
				var image = fs.readFileSync(results[i]['images_url']);
				results[i]['image'] = getFrontImageBase64(results[i]['extension']) + (new Buffer(image).toString('base64'));
			} catch (e) {
				console.log(e);
				msg.sendMsg(res, 500, {
					error: e
				});
				return;
			}
		}

		msg.sendMsg(res, 200, {
			data: results
		});
	});


};

function getExtension (imageType) {
	if (imageType === 'image/jpeg') {
		return 'jpg';
	} else if (imageType === 'image/png') {
		return 'png';
	}
}
function getFrontImageBase64 (extension) {
	if (extension === 'jpg') {
		return 'data:image/jpeg;base64,'
	} else if (extension === 'png') {
		return 'data:image/png;base64,';
	}
}
// function getRevisedSN (sn, type) {
// 	return type + ':' + sn;
// }
// function deleteItem (userId, itemId, callback) {
// 	var query;

// 	query = 'SELECT userId FROM Item '
// 		+ 'WHERE itemId='
// 		+ '"' + itemId + '"';

// 	async.waterfall([
// 		function (callback) {
// 			conn.query(query, function (error, results) {
// 				if (error) {

// 				}
// 			});
// 		}
// 		])
// }