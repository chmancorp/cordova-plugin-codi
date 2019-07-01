
var exec = require('cordova/exec');

var PLUGIN_NAME = 'MiPlugin';

var MiPlugin = {
  
  echo: function(codr,idh,phone,gId, cb){
    exec(cb, null, PLUGIN_NAME, "echo", [codr,idh,phone,gId,cb]);
  }
};

module.exports = MiPlugin;
