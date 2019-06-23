
var exec = require('cordova/exec');

var PLUGIN_NAME = 'MiPlugin';

var MiPlugin = {
  saludo: function (name, successCallback, errorCallback){
        exec(successCallback, errorCallback, PLUGIN_NAME, "saludar", [name]);
  },
  echo: function(success, error){
    exec(success, error, PLUGIN_NAME, "echo", []);
  },
  test: function(np,cb){
    exec(cb,null,PLUGIN_NAME,"test",[np]);
  }
};

module.exports = MiPlugin;
