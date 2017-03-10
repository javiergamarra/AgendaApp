package com.nhpatt.agendaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final List<Talk> talks = new ArrayList<Talk>() {{
        add(new Talk("09:00", "Reactive Programming and RxJava2"));
        add(new Talk("10:00", "Workshop of Android Programming"));
    }};

    private TalkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView listView = (RecyclerView) findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TalkAdapter(this, talks, this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.row) {
            startActivity(new Intent(this, DetailTalkActivity.class));
        } else {
            Talk talk = talks.get((Integer) v.getTag());
            talk.setFavorited(!talk.isFavorited());
            adapter.notifyDataSetChanged();
        }
    }
}
