'use strict';

var formdataParser = require('parse-formdata');

exports.parseFormdata = function (req, res, callback) {
	formdataParser(req, function (error, data) {
		if (error) {
			console.log('formdata parsing error');
			res.status(400).send({
				error: error
			});
			return;
		}
		req.body = data.fields;

		callback();
	});
};