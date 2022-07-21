package io.ionic.starter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class FirebaseMessageReceiver
  extends FirebaseMessagingService {


  @Override
  public void onNewToken(String token) {
    super.onNewToken(token);
    Log.d("newToken", token);
//Add your token in your sharepreferences.
    getSharedPreferences("_", MODE_PRIVATE).edit().putString("fcm_token", token).apply();
  }


  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {


    String title = remoteMessage.getNotification().getTitle();
    String text = remoteMessage.getNotification().getBody();

    final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";

    NotificationChannel channel = new NotificationChannel(
      CHANNEL_ID,
      "Heads Up Notification",
      NotificationManager.IMPORTANCE_HIGH
    );

    Notification.Builder notification =
      new Notification.Builder(this, CHANNEL_ID)
        .setContentTitle(title)
        .setContentText(text)
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setAutoCancel(true);

    NotificationManagerCompat.from(this).notify(1, notification.build());

    super.onMessageReceived(remoteMessage);
  }
}
