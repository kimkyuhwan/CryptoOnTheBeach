'use strict';

var Tx = require('ethereumjs-tx'),
	Web3 = require('web3'),
	mysql = require('mysql'),
	web3 = new Web3('http://localhost:8102'),
	config = require('../config'),
	secondhandProduct = new web3.eth.Contract(config.secondhandProduct.interface, config.secondhandProduct.address),
	secondhandMarket = new web3.eth.Contract(config.secondhandMarket.interface, config.secondhandMarket.address),
	ducktoken = new web3.eth.Contract(config.ducktoken.interface, config.ducktoken.address),
	conn = mysql.createConnection(config.development_database),
	async = require('async'),
	msg = require('../tool/msg');


exports.barter = function (account, req, res, callback) {
	var sn = req.body.sn,
		to = req.body.to,
		price = req.body.price,
		rawTx,
		tx,
		privateKey = new Buffer(account.privateKey.slice(2), 'hex');

	if (sn, to, price) {
		async.waterfall([
			function (callback) {
				web3.eth.getTransactionCount(account.address, function (error, results) {
					if (error) {
						callback(error);
						return;
					} else {
						rawTx = {
							nonce: nonce,
							gasLimit: config.gasLimit,
							to: config.barter.address,
							data: secondhandMarket.methods.barter(sn, to, price)
						};
						tx = new Tx(rawTx);
						tx.sign(privateKey);
						privateKey.clear();
						callback(tx.serialize());
					}
				});
			}, function (serializedTx) {
				web3.eth.sendSignedTransaction('0x' + serializedTx.toString('hex'))
				.on('transactionHash', function (hash) {
					msg.sendMsg(res, 200);
				})
				.on('receipt', function (data) {

				})
				.on('error', function (error) {

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

exports.traceProductOwnership = function (req, res) {
	var sn = req.query.sn;

	if (sn) {
		sn = web3.utils.toHex(sn);

		secondhandProduct.getPastEvents('TransferProductOwnership', {
			filter: {
				sn: sn
			},
			fromBlock: 35000,
			toBlock: 'latest'
		}, function (error, events) {
			if (error) {
				console.log(error);
				msg.sendMsg(res, 400, {
					error: error
				});
			} else {
				console.log('events', events);
				msg.sendMsg(res, 200, {
					data: events
				});
			}
		});
	} else {
		msg.sendMsg(res, 400);
	}
};
// exports.purchase = function (account, req, res, callback) {
// 		var seller = req.body.seller,
// 		sellerId = req.body.sellerId,
// 		buyerId = req.body.userId,
// 		timestamp = req.body.timestamp,
// 		itemId = req.body.itemId,
// 		score = req.body.score || 1,
// 		price = req.body.price,
// 		nonce,
// 		tx,
// 		serializedTx,
// 		rawTx,
// 		privateKey = new Buffer(account.privateKey.slice(2), 'hex');

// 		console.log('buyer', account.address);
// 		console.log('seller', seller);
// 		console.log('itemId', itemId);

// 		if (seller && timestamp && itemId && score && price && privateKey) {
// 			web3.eth.getTransactionCount(account.address, function (error, result) {
// 				if (error) {
// 					console.log(error);
// 					msg.sendMsg(res, 500);
// 					return;
// 				}
// 				nonce = result;
// 				rawTx = {
// 					nonce: nonce,
// 					// gasPrice: '10',
// 			  		gasLimit: 300000,
// 					to: config.barter.address,
// 					value: 0x0,
// 					data: (function () {
// 						try {
// 							return barter.methods.purchase(seller, timestamp, itemId, score, price).encodeABI()
// 						} catch (e) {
// 							if (e) {
// 								console.log(e);
// 								msg.sendMsg(res, 500);
// 							}
// 						}
// 					})()
// 				};

// 				tx = new Tx(rawTx);
// 				try {
// 					tx.sign(privateKey);
// 				} catch (e) {
// 					console.log(e);
// 					msg.sendMsg(res, 400, {
// 						error: String(e)
// 					});
// 					return;
// 				}
// 				serializedTx = tx.serialize();

// 				web3.eth.sendSignedTransaction('0x' + serializedTx.toString('hex'))
// 				.on('transactionHash', function (hash) {
// 					msg.sendMsg(res, 200, {
// 						data: {
// 							hash: hash
// 						}
// 					});
// 				})
// 				.on('error', function (error) {
// 					console.log('error', error);
// 				});
// 			});
// 		} else {
// 			msg.sendMsg(res, 400);
// 		}

// };

exports.getBalance = function (req, res) {
	var address,
		query,
		userId = req.body.userId,
		ethAddress,
		ethBalance,
		duckBalance;

	if (userId) {
		query = 'SELECT ethAddress FROM User WHERE userId='
			+ '"' + userId + '"';

		async.waterfall([
			function (callback) {
				conn.query(query, function (error, result) {
					if (error) {
						callback(error);
					} else if (result.length > 0) {
						callback(error, result[0]['ethAddress']);
					} else {
						callback('User Not Found');
					}
				});
			},
			function (address, callback) {
				if (address) {
					ethAddress = address;
					web3.eth.getBalance(address).then(function (response) {
						console.log(response);
						ethBalance = response;
						callback();
					}).catch(function (error) {
						callback(error);
					});
				} else {
					callback('Get Balance Function Error');
				}
			},
			function (callback) {
				if (ethAddress) {
					ducktoken.methods.balanceOf(ethAddress).call().then(function (response) {
						duckBalance = response;
						callback();
					}).catch(function (error) {
						console.log(error);
						callback(error);
					});
				}
			}
			], function (error) {
				if (error) {
					console.log(error);
					msg.sendMsg(res, 400, {
						error: error
					});
				} else {
					msg.sendMsg(res, 200, {
						data: {
							ethAddress: ethAddress,
							ethBalance: ethBalance,
							duckBalance: duckBalance
						}
					});
				}
			});
	} else {
		msg.sendMsg(res, 400);
	}

};

// exports.getBuyerlog = function (req, res) {
// 	var buyer = req.body.buyer;

// 	if (buyer) {
// 		async.waterfall([
// 			function (callback) {
// 				barter.methods.getBuyerlogLength(buyer).call().then(function (length) {
// 					if (length > 0) {
// 						callback(length);
// 					} else {
// 						callback('Empty Buyer');
// 					}
// 				});
// 			},
// 			function (length, callback) {
// 				var promises = [];

// 				for (var i = length - 1; i >= 0; i--) {
// 					promises.push(barter.methods.buyerlog(buyer, i).call());
// 				}

// 				Promises.all(promises).then(function (responses) {
// 					callback(undefined, responses)
// 				}).catch(function (error) {
// 					callback(error);
// 				});
// 			}
// 			], function (error, data) {
// 				if (error) {
// 					msg.sendMsg(res, 400, {
// 						error: error
// 					});
// 				} else {
// 					msg.sendMsg(res, 200, {
// 						data: data
// 					});
// 				}
// 			});
// 	} else {
// 		msg.sendMsg(res, 400);
// 	}
// };
// exports.getSellerlog = function (req, res) {
// 	var seller = req.query.seller,
// 		length;

// 	console.log(seller);

// 	if (seller) {
// 		async.waterfall([
// 			function (callback) {
// 				barter.methods.getSellerlogLength(seller).call().then(function (_length) {
// 					if (_length > 0) {
// 						length = _length;
// 						callback();
// 					} else {
// 						callback('Empty Seller');
// 					}
// 				});
// 			},
// 			function (callback) {
// 				var promises = [];

// 				for (var i = length - 1; i >= 0; i--) {
// 					promises.push(barter.methods.sellerlog(seller, i).call());
// 				}

// 				Promise.all(promises).then(function (responses) {
// 					callback(undefined, responses);
// 				}).catch(function (error) {
// 					callback(error);
// 				});
// 			}
// 			], function (error, data) {
// 				if (error) {
// 					msg.sendMsg(res, 400, {
// 						error: error
// 					});
// 				} else {
// 					msg.sendMsg(res, 200, {
// 						data: data
// 					});
// 				}
// 			});
// 	} else {
// 		msg.sendMsg(res, 400);
// 	}
// };

exports.getBlock = function (req, res) {
	var blockHeight = req.params.blockHeight;

	web3.eth.getBlock(blockHeight).then(function (response) {

	}).catch(function (error) {

	});
};

exports.getBlockNumber = function (req, res) {
	web3.eth.getBlockNumber().then(function (blocknumber) {
		msg.sendMsg(res, 200, {
			data: {
				blocknumber: blocknumber
			}
		})
	}).catch(function (error) {
		msg.sendMsg(res, 500, error);
	});
};