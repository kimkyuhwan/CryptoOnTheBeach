var express = require('express'),
	router = express.Router(),
	blockchainAPI = require('../controller/blockchain_api.js');

router.post('/get-ethereum-account', blockchainAPI.getEthereumAccount);
router.post('/register-item', blockchainAPI.recoverKeystore, blockchainAPI.registerItem);
// router.post('/barter-item', blockchainAPI.recoverKeystore, blockchainAPI.barterItem);
// router.get('/trace-product', blockchainAPI.recoverKeystore, blockchainAPI.traceProduct);

module.exports = router;
