
var exec = require('cordova/exec');

var PLUGIN_NAME = 'MiPlugin';

var MiPlugin = {
  saludo: function (name, successCallback, errorCallback){
        exec(successCallback, errorCallback, PLUGIN_NAME, "saludar", [name]);
  },
  echo: function(phrase, cb){
    exec(cb, null, PLUGIN_NAME, "echo", [phrase]);
  }
};

module.exports = MiPlugin;
