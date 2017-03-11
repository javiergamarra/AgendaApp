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

        Talk talk = (Talk) getIntent().getSerializableExtra("talk");
        Log.d(TAG, talk.getTitle());
    }


}
