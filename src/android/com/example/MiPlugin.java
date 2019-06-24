/**
 */
package com.example;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import java.util.Date;

public class MiPlugin extends CordovaPlugin {
  private static final String TAG = "MiPlugin";

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    CallbackContext callbackContext = new CallbackContext();
    Log.d(TAG, "Inicializando MiPlugin");
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

    if(action.equals("echo")){
      String codr = args.getString(0);
      String idh = args.getString(1);
      String phone = args.getString(2);
      String gId = args.getString(3);

      // final PluginResult result = new PluginResult(PluginResult.Status.OK, "Hola todo el... "+phrase);
      // String myString = "Some string";
      //   callbackContext.success(myString);
      // getIdn();
      this.callbackContext.success(phrase);
      Log.d("TAG", this.callbackContext);
      return true;
    }
  }

}
