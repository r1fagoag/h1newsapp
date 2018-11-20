package com.example.rkjc.news_app_2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    //    private TextView mURLDisplayTextView;
//    private TextView mSearchResultsTextView;
    private ProgressBar mProgressBar;

    private RecyclerView mRecyclerView;
    private NewsRecyclerViewAdapter mAdapter;
    private ArrayList<NewsItem> news = new ArrayList<>();


    private NewsItemViewModel mNewsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        mURLDisplayTextView = (TextView) findViewById(R.id.url_display);
//        mSearchResultsTextView = (TextView) findViewById(R.id.news_search_result);
//        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);

        mNewsViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);

        mAdapter = new NewsRecyclerViewAdapter(this, news);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(this));
        SyncNewsUtilities.scheduleSync(this);
        mNewsViewModel.getAllNews().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                mAdapter.setNews(newsItems);
                mAdapter.notifyDataSetChanged();

            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            URL url = NetworkUtils.buildUrl(getString(R.string.News_API_Key));
//            mURLDisplayTextView.setText(url.toString());
//            NewsQueryTask task = new NewsQueryTask();
//            task.execute(url);

            mNewsViewModel.syncNews(url);


            return true;
        }
        return super.onOptionsItemSelected(item);
    }
/*
    class NewsQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            String newsSearchResults = "";

            try {
                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return newsSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgressBar.setVisibility(View.GONE);
            if (s != null && !s.equals("")) {

                news = JsonUtils.parseNews(s);
                mAdapter.mNews.addAll(news);
                mAdapter.notifyDataSetChanged();
//              mSearchResultsTextView.setText(s);
            }
        }

    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
