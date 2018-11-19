package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsRepository {
    private NewsItemDao mNewsDao;
    private LiveData<List<NewsItem>> mAllNews;

    NewsRepository(Application application) {
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application);
        mNewsDao = db.newsDao();
        mAllNews = mNewsDao.loadAllNewsItems();
    }

    public LiveData<List<NewsItem>> getAllNews() {
        new allNewsAsyncTask(mNewsDao).execute();
        return mAllNews;
    }

    public void syncNews(URL url) {
        new syncNewsAsyncTask(mNewsDao).execute(url);

    }

    private class allNewsAsyncTask extends AsyncTask<Void, Void, Void> {

        private NewsItemDao mAsyncTaskDao;

        allNewsAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mAllNews = mAsyncTaskDao.loadAllNewsItems();
            return null;
        }
    }

    private class syncNewsAsyncTask extends AsyncTask<URL, Void, Void> {

        private NewsItemDao mAsyncTaskDao;
        syncNewsAsyncTask( NewsItemDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(URL... urls) {
            String newsSearchResults = "";
            ArrayList<NewsItem> news = new ArrayList<>();

            mAsyncTaskDao.clearAll();

            try {
                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            news = JsonUtils.parseNews(newsSearchResults);

            mAsyncTaskDao.insert(news);

            return null;
        }
    }






}
