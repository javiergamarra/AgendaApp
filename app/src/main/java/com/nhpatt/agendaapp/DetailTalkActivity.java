package com.nhpatt.agendaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailTalkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_talk);

        Talk talk = (Talk) getIntent().getSerializableExtra("talk");

        TextView title = (TextView) findViewById(R.id.talk_title);
        title.setText(talk.getTitle());

        TextView time = (TextView) findViewById(R.id.talk_time);
        time.setText(talk.getTime());
    }

}
