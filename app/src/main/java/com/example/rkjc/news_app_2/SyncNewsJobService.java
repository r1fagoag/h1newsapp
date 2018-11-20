package com.example.rkjc.news_app_2;

import android.annotation.SuppressLint;


import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import android.os.AsyncTask;

public class SyncNewsJobService extends JobService {
    private AsyncTask<Void, Void, Void> mSyncTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mSyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                SyncNewsTask.synchronizeNews(getApplicationContext());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(jobParameters, false);
            }


        };

        mSyncTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if(mSyncTask != null) mSyncTask.cancel(true);
        return true;
    }
}
