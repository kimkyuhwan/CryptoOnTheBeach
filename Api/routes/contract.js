var express = require('express'),
	router = express.Router(),
	contract = require('../controller/contract.controller.js'),
	validate = require('../controller/validate.controller.js');

// router.post('/remittance', [validateController.validateKeystore, contract.sendToken]);
router.post('/barter', [validate.validateKeystore, contract.barter]);
router.get('/trace-product', contract.traceProductOwnership);
// router.get('/balance', contract.getBalance);
// router.get('/buyerlog', contract.getBuyerlog);
// router.get('/sellerlog', contract.getSellerlog);
router.get('/block', contract.getBlock);
router.get('/blocknumber', contract.getBlockNumber);

module.exports = router;
