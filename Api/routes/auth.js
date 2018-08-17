var express = require('express'),
	router = express.Router(),
	auth = require('../controller/auth.controller.js');

router.post('/signup', auth.signUp);
router.post('/signin', auth.signIn);
router.get('/dup-userid', auth.isUserIdDuplicated);


module.exports = router;
