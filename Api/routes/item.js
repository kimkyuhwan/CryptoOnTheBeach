var express = require('express'),
	router = express.Router(),
	item = require('../controller/item.controller.js'),
	validate = require('../controller/validate.controller.js');

router.post('/reg', [validate.validateKeystore, item.registerItem]);
router.post('/unreg', item.unregisterItem);
router.get('/list', item.getItems);

module.exports = router;
