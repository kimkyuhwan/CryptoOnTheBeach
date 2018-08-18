var express = require('express'),
	router = express.Router(),
	blockchainAPI = require('../controller/blockchain_api.js'),
	parse = require('../controller/parse.js');

router.post('/get-ethereum-account', parse.parseFormdata, blockchainAPI.getEthereumAccount);
router.post('/register-item', parse.parseFormdata, blockchainAPI.recoverKeystore, blockchainAPI.registerItem);
router.post('/barter-item', parse.parseFormdata, blockchainAPI.recoverKeystore, blockchainAPI.barterItem);
router.get('/trace-product', blockchainAPI.traceProduct);
router.get('/get-block', blockchainAPI.getBlock);
router.get('/balances', blockchainAPI.getBalances);

module.exports = router;
