/**
 */
package com.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build;  

import com.google.firebase.FirebaseApp;

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
  private static  boolean IS_AT_LEAST_LOLLIPOP = Build.VERSION.SDK_INT >= 21;
  Context context; 
  Bundle extras;
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    this.context = IS_AT_LEAST_LOLLIPOP ? cordova.getActivity().getWindow().getContext() : cordova.getActivity().getApplicationContext(); 
    this.extras = this.cordova.getActivity().getIntent().getExtras();
    this.cordova.getThreadPool().execute(new Runnable() {
      public void run() {
          Log.d(TAG, "Starting Firebase plugin");
          FirebaseApp.initializeApp(context);
          // mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
          // mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
          // if (extras != null && extras.size() > 1) {
          //     if (FirebasePlugin.notificationStack == null) {
          //         FirebasePlugin.notificationStack = new ArrayList<Bundle>();
          //     }
          //     if (extras.containsKey("google.message_id")) {
          //         extras.putBoolean("tap", true);
          //         notificationStack.add(extras);
          //     }
          // }
      }
  });
    Log.d(TAG, "Inicializando MiPlugin");

  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if(action.equals("echo")){
      FirebaseConfig firebaseConfig = new FirebaseConfig(this.cordova.getActivity());
      String codr = args.getString(0);
      String idh = args.getString(1);
      String phone = args.getString(2);
      String gId = args.getString(3);
      String keySource = firebaseConfig.getKeySource(codr,idh,phone);
      String gIdDecrypted = firebaseConfig.decrypGId(keySource.substring(0,32),keySource.substring(32,64),gId);
      firebaseConfig.generateIdN(gIdDecrypted,callbackContext); 
      //firebaseConfig.generateIdN(codr, idh, phone, gId, callbackContext);

      // callbackContext.success(result);
      Log.d("TAG", phone);
    }
    return true;

  }

}
