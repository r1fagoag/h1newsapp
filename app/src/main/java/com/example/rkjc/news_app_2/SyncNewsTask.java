package com.example.rkjc.news_app_2;

import android.content.Context;

import java.net.URL;

public class SyncNewsTask {

    public static void synchronizeNews(Context context) {
        NewsRepository mRepository = new NewsRepository(context);
        URL url = NetworkUtils.buildUrl(context.getString(R.string.News_API_Key));
        mRepository.syncNews(url);

    }
}

