package com.example.rkjc.news_app_2;

import android.content.Context;
import android.util.Log;

import java.net.URL;

public class SyncNewsTask {
    private static final String TAG = "SynchronizeTask";

 //   public static void executeTask(Context context, String action) {
 //   }

    public static void cancelNotification(Context context) {
        NotificationUtils.clearAllNotifications(context);
    }

    public static void synchronizeNews(Context context) {


        NewsRepository mRepository = new NewsRepository(context);
        URL url = NetworkUtils.buildUrl(context.getString(R.string.News_API_Key));
        mRepository.syncNews(url);
        NotificationUtils.NotifySync(context);
        Log.i(TAG, "Sync Success");

    }



}

