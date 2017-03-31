package com.nhpatt.agendaapp;

import android.app.Application;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;

public class MyAgendaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(MainActivity.TAG, "Creating app...");

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
