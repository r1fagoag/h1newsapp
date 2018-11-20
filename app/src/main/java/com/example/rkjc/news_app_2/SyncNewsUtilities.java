package com.example.rkjc.news_app_2;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;


public class SyncNewsUtilities {
    private static final int SYNC_SCHEDULE_SECONDS = 10;
    private static final int SYNC_FLEXTIME_SECONDS = 10;
    private static final String SYNC_JOB_TAG = "sync_job_tag";

    private static boolean sInitialized;

    synchronized public static void scheduleSync(@NonNull final Context context) {
        if(sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job syncJob = dispatcher.newJobBuilder()
                .setService(SyncNewsJobService.class)
                .setTag(SYNC_JOB_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SYNC_SCHEDULE_SECONDS,
                        SYNC_SCHEDULE_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(syncJob);
        sInitialized = true;
    }

}
