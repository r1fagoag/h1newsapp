package com.example.rkjc.news_app_2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotificationUtils {
    public static final int SYNC_NOTIFICATION_ID = 111;

    public static final int SYNC_NOTIFICATION_PENDING_ID = 222;

    private static final String SYNC_NOTIFICATION_CHANNEL_ID = "sync_notification_channel";
    private static final int ACTION_CANCEL_PENDING_ID = 333;

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void NotifySync(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    SYNC_NOTIFICATION_CHANNEL_ID,
                    "sync_notification_channel",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,SYNC_NOTIFICATION_CHANNEL_ID)
                .setContentTitle("News Sync Notification")
                .setContentText("News retrieved! The display has been updated.")
                .setSmallIcon(R.drawable.ic_android_black_24dp)
  //              .setStyle(new NotificationCompat.BigTextStyle().bigText("News retrieved! The display has been updated."))
  //              .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(contentIntent(context))
                .addAction(cancelAction(context))
                .setAutoCancel(false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(SYNC_NOTIFICATION_ID, notificationBuilder.build());
        Log.i("Notification Generator", "Notification Sent");
    }

    public static NotificationCompat.Action cancelAction(Context context) {
        Intent cancelIntent = new Intent(context, SyncNotificationIntentService.class);
        cancelIntent.setAction("0");

        PendingIntent cancelPendingIntent = PendingIntent.getService(
                context,
                ACTION_CANCEL_PENDING_ID,
                cancelIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action cancelAct = new NotificationCompat.Action(R.drawable.ic_cancel_black_24dp,
                "Close Notification",
                cancelPendingIntent);

        return cancelAct;

    }



    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                SYNC_NOTIFICATION_PENDING_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_android_black_24dp);
        return largeIcon;
    }
}
