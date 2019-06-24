
var exec = require('cordova/exec');

var PLUGIN_NAME = 'MiPlugin';

var MiPlugin = {
  saludo: function (name, successCallback, errorCallback){
        exec(successCallback, errorCallback, PLUGIN_NAME, "saludar", [name]);
  },
  echo: function(codr,idh,phone,gId, cb){
    exec(cb, null, PLUGIN_NAME, "echo", [codr,idh,phone,gId,cb]);
  },
  test: function(np,cb){
    exec(cb,null,PLUGIN_NAME,"test",[np]);
  }
};

module.exports = MiPlugin;
