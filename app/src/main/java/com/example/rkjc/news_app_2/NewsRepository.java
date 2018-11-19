package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class NewsRepository {
    private NewsItemDao mNewsDao;
    private LiveData<List<NewsItem>> mAllNews;

    NewsRepository(Application application) {
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application);
        mNewsDao = db.newsDao();
    }


}
