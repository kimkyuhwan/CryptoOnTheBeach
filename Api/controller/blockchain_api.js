'use strict';

var Web3 = require('Web3'),
	web3 = new Web3('http://localhost:8102'),
	Tx = require('ethereumjs-tx'),
	async = require('async'),
	config = require('../config'),
	msg = require('../tool/msg'),
	time = require('../tool/time'),
	secondhandProduct = new web3.eth.Contract(config.secondhandProduct.interface, config.secondhandProduct.address),
	secondhandMarket = new web3.eth.Contract(config.secondhandMarket.interface, config.secondhandMarket.address),
	ducktoken = new web3.eth.Contract(config.ducktoken.interface, config.ducktoken.address);

exports.getEthereumAccount = function (req, res) {
	var keystore,
		accountInfo,
		password = req.body.password,
		data;

	if (password) {
		accountInfo = web3.eth.accounts.create(password);
		keystore = web3.eth.accounts.encrypt(accountInfo.privateKey, password);
		msg.sendMsg(res, 200, {
			data: {
				keystore: keystore
			}
		});
	} else {
		msg.sendMsg(res, 400);
	}
};

exports.recoverKeystore = function (req, res, callback) {
	var keystore = req.body.keystore,
	password = req.body.password,
	b,
	account;

	if (keystore && password) {
		try {
			account = web3.eth.accounts.decrypt(keystore, password);
		} catch (e) {
			console.log('error', e);
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

exports.registerItem = function (account, req, res, callback) {
	var sn = req.body.sn,
	rawTx,
	privateKey,
	tx;

	sn = web3.utils.toHex(sn);

	if (sn) {
		async.waterfall([
			function (callback) {
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
				});
			},
			function (serializedTx, callback) {

				web3.eth.sendSignedTransaction('0x' + serializedTx.toString('hex'))
				.on('error', function (error) {
					callback(error);
				})
				.on('receipt', function (data) {
					msg.sendMsg(res, 200, {
						data: data
					});
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

exports.barterItem = function (account, req, res, callback) {
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
				.on('receipt', function (data) {
					msg.sendMsg(res, 200);
				})
				.on('error', function (error) {
					msg.sendMsg(res, 400, {
						error: error
					});
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

exports.traceProduct = function (req, res, callback) {
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
				msg.sendMsg(res, 200, {
					data: events
				});
			}
		});
	} else {
		msg.sendMsg(res, 400);
	}
};

exports.getBlock = function (req, res) {
	var blockHeightList = JSON.parse(req.query.blockHeightList),
		functionList = [];

	if (typeof(blockHeightList) === 'object') {
		for (var blockHeight in blockHeightList) {
			functionList.push(web3.eth.getBlock(blockHeight));
		}

		Promise.all(functionList).then(function (values) {
			msg.sendMsg(res, 200, {
				data: values
			});
		}).catch(function (error) {
			console.log('get block error', error);
			msg.sendMsg(res, 500, {
				error: error
			});
		});
		
	} else {
		msg.sendMsg(res, 400);
	}

};

exports.getBalances = function (req, res) {
	var address = req.query.address;

	console.log(address);
	if (address) {
		Promise.all([
			web3.eth.getBalance(address),
			ducktoken.methods.balanceOf(address).call()])
		.then(function (results) {
			msg.sendMsg(res, 200, {
				data: {
					ethereum: results[0],
					token: results[1]
				}
			});
		}).catch(function (errors) {
			console.log("errors", errors);
			msg.sendMsg(res, 500, {
				error: errors
			});
		});
	} else {
		msg.sendMsg(res, 400);
	}
};












