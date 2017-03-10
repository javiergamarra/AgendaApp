package com.nhpatt.agendaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class DetailTalkActivity extends AppCompatActivity {

    public static final String TAG = "AgendaApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_talk);

        Log.d(TAG, "Created");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "Started");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "Resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "Paused");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "Stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "Destroyed");
    }
}
