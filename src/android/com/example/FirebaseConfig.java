package com.example;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.cordova.CallbackContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FirebaseConfig {
    private String androidID = "764d2cb7da3280eb"; //AndroidIDPrivada
    private String token = null;
    private FirebaseApp myApp;
    private int numIntent = 0;
    private Activity activity;
    CallbackContext callbackContext;
    public FirebaseConfig(Activity activity){
        this.activity = activity;
        // String gId = decrypGId("164677","7f81804bf3b48a6ff81bf0bbb2c4bf01f9ccbec3-com.mitiendita.codi","9617094922","fcaab4e94de150e986d954e425569a16");
        // if(!gId.isEmpty()){
        //     configurarFirebaseAppBanxico(gId);
        // }
    }

    public void generateIdN(String codR,String idH,String nc,String gId, CallbackContext callbackContext){
        this.callbackContext = callbackContext;
        String gIdDecrypt = decrypGId(codr, idH,nc,"fcaab4e94de150e986d954e425569a16");
        if(!gIdDecrypt.isEmpty()){
            configurarFirebaseAppPrivada();
            configurarFirebaseAppBanxico(gId);

        }
    }
    public String decrypGId(String codR,String idH,String nc,String gId){
        String sha512Codr = new String(Hex.encodeHex(DigestUtils.sha512(codR)));
        String result = new String(Hex.encodeHex(DigestUtils.sha512(sha512Codr+ idH + nc )));
        String aesKey = result.substring(0,32);
        String aesiv = result.substring(32,64);
        String hmacKey = result.substring(64,128);
        try {
            IvParameterSpec iv = new IvParameterSpec(Hex.decodeHex(aesiv.toCharArray()));
            SecretKeySpec sKeySpec = new SecretKeySpec(Hex.decodeHex(aesKey.toCharArray()),"AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE,sKeySpec,iv);
            byte[] original = cipher.doFinal(Hex.decodeHex(gId.toCharArray()));
            String decryption = new String(original, "UTF-8");
            return decryption;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (DecoderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new String();
    }
    public void generateSha512Hex(){

    }
    private void configurarFirebaseAppPrivada()
    {
        Log.d("Msj: ", "configurarFirebaseAppPrivada");
        try {
            String cGoogleID = "683134177964"; //IDProyectoPrivado
            setInitializeAppPrivada(cGoogleID, androidID);
            new TokenPrivada().execute(cGoogleID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarFirebaseAppBanxico(String cGoogleID)
    {
        Log.d("Msj: ", "configurarFirebaseAppBanxico");
        try {
            //String cGoogleID = "201247069219"; //IDProyectoBanxico
            setInitializeAppBanxico(cGoogleID, androidID);
            new TokenBanxico(this.callbackContext).execute(cGoogleID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInitializeAppPrivada(String cGoogleID, String androidID)
    {
        Log.d("Privada: ", "cGoogleID: " + cGoogleID + " androidID:" + androidID);
        FirebaseOptions.Builder builder = new FirebaseOptions.Builder()
                .setApplicationId("1:"+ cGoogleID +":android:" + androidID);
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps(activity);
        for (FirebaseApp app: firebaseApps) {
            if (app.getName().equals("privada"))
                app.delete();
        }
        myApp = FirebaseApp.initializeApp(activity, builder.build(), "privada");
        Log.d("Msj: ", "configurado:" + myApp.getName());
    }

    private void setInitializeAppBanxico(String cGoogleID, String androidID)
    {
        Log.d("Banxico: ", "cGoogleID: " + cGoogleID + " androidID:" + androidID);
        FirebaseOptions.Builder builder = new FirebaseOptions.Builder()
                .setApplicationId("1:"+ cGoogleID +":android:" + androidID);
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps(activity);
        for (FirebaseApp app: firebaseApps) {
            if (app.getName().equals("banxico"))
                app.delete();
        }
        myApp = FirebaseApp.initializeApp(activity, builder.build(), "banxico");
        Log.d("Msj: ", "configurado:" + myApp.getName());
    }

    private class TokenPrivada extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                token = FirebaseInstanceId.getInstance().getToken(params[0],
                        FirebaseMessaging.INSTANCE_ID_SCOPE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("*TokenPrivada: " , token + " ");
            /*new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                   // txtTokenPrivada.setText("Token privada:\n" + token);
                }
            });*/
            return null;
        }
    }
    private class TokenBanxico extends AsyncTask<String, Void, Void>{
        CallbackContext callbackContext;
        public TokenBanxico(CallbackContext callbackContext){
            this.callbackContext = callbackContext;
        }
        @Override
        protected Void doInBackground(String... params) {
            try {
                token = FirebaseInstanceId.getInstance().getToken(params[0],
                        FirebaseMessaging.INSTANCE_ID_SCOPE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.callbackContext.success(token);
            Log.d("*TokenBanxico: " , token + " ");

            /*new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    //txtTokenBanxico.setText("Token Banxico:\n" + token);
                }
            });*/
            return null;
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(activity.getBaseContext(), "Recibi notificacion: " + ++numIntent ,
                    Toast.LENGTH_SHORT).show();
        }
    };
}
