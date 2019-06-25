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
  final Context context = this.cordova.getActivity().getApplicationContext();
  final Bundle extras = this.cordova.getActivity().getIntent().getExtras();
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    
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
    FirebaseConfig firebaseConfig = new FirebaseConfig(this.context);
    if(action.equals("echo")){
      FirebaseConfig firebaseConfig = new FirebaseConfig(this.context);
      Log.d("TAG", args);
      Log.d("TAG",firebaseConfig);
      String codr = args.getString(0);
      String idh = args.getString(1);
      String phone = args.getString(2);
      String gId = args.getString(3);

      firebaseConfig.generateIdN(codr, idh, phone, gId, callbackContext);

      // callbackContext.success(result);
      Log.d("TAG", phone);
    }
    return true;

  }

}
