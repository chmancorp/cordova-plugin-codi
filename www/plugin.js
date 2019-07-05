
var exec = require('cordova/exec');

var PLUGIN_NAME = 'MiPlugin';

var MiPlugin = {
  
  echo: function(codr,idh,phone,gId, cb){
    exec(cb, null, PLUGIN_NAME, "echo", [codr,idh,phone,gId,cb]);
  },
  getSHA512: function(cadena,success,error){
    exec(success,null,PLUGIN_NAME,"getSHA512",[cadena])
  }
};

module.exports = MiPlugin;
