'use strict';

exports.sendMsg = function (res, msgNumber, info) {
  var contents = {};

  contents['status'] = msgNumber;
  contents.message = getMessage(msgNumber);
  
  if (info) {
    if (info.data) {
      contents['data'] = info.data;
    }
    if (info.error) {
      contents['error'] = info.error;
    }
    if (info.message) {
      contents.message = info.message;
    }
  }
  res.status(msgNumber).send(contents);
}

function getMessage (msgNumber) {
  var message = {
    '200': 'OK',
    '201': 'Created',
    '400': 'Bad Request',
    '401': 'Unauthorized',
    '403': 'Forbidden',
    '404': 'Not Found',
    '500': 'Internal Server Error',
    '503': 'Service Unavailable'
  };

  return message[msgNumber];
}