package com.example.rkjc.news_app_2;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class SyncNotificationIntentService extends IntentService {

    public SyncNotificationIntentService() {
        super("SyncNotificationIntentService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
  //      String action = intent.getAction();
  //      SyncNewsTask.executeTask(this, action);
        SyncNewsTask.cancelNotification(this);

    }
}
