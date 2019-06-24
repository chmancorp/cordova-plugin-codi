package com.example;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

//import com.bandicoot.heriberto.untestfirebase.MainActivity;
//import com.bandicoot.heriberto.untestfirebase.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("Receive", "Mensaje Recibido");
        try{
            Log.d("dataMsj",remoteMessage.getData().toString());
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            Log.d("JSON_OBJECT", object.toString());
        }catch(Exception e)
        {
            Log.e("Error", e.getMessage() + " causa: " + e.getCause() );
        }
        if(remoteMessage.getNotification() !=null) {
            Log.d("RM noti.body", remoteMessage.getNotification().getBody());
            mostrarNotificacion(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
        else if(remoteMessage.getData() != null) {
            JSONObject dataObject = null;
            try {
                dataObject = new JSONObject(remoteMessage.getData().get("data"));
                Log.d("RM dataObject", dataObject.toString());
                //mostrarNotificacion(dataObject.getString("title"), dataObject.getString("body"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    /*private void mostrarNotificacion(String title, String body) {
        Log.d("mostrarNotificacion", "Title: " + title + " Body: " + body);
        Intent intentOpen = new Intent("myToast");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intentOpen);
        Intent intent = new Intent(this, MainActivity.class);
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setOnlyAlertOnce(true);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());
    }*/
}
