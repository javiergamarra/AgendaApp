package com.nhpatt.agendaapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Talk> talks;
    private TalkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        talks = new ArrayList<>();
        adapter = new TalkAdapter(this, talks, this);
        RecyclerView listView = (RecyclerView) findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);

        loadTalks();
    }

    @Override
    protected void onPause() {
        super.onPause();

        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("talks", (ArrayList<Talk>) talks);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        talks = (List<Talk>) savedInstanceState.getSerializable("talks");
    }

    private void loadTalks() {
        if (talks.isEmpty()) {
            new Thread(new TalkProcessor()).start();
        }
    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();
        Talk talk = talks.get(position);
        if (v.getId() == R.id.row) {
            Intent intent = new Intent(this, DetailTalkActivity.class);
            intent.putExtra("talk", talk);
            startActivity(intent);
        } else {
            talk.setFavorited(!talk.isFavorited());

            getSharedPreferences().edit()
                    .putBoolean(String.valueOf(talk.getId()), talk.isFavorited()).apply();

            adapter.notifyDataSetChanged();
        }
    }

    private SharedPreferences getSharedPreferences() {
        return getSharedPreferences("AgendaApp", Context.MODE_PRIVATE);
    }

    private static class TalkProcessor implements Runnable {

        public void run() {
            try {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://data.agenda.wedeploy.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TalksService service = retrofit.create(TalksService.class);

                final Call<List<Talk>> call = service.allTalks();
                List<Talk> elements = call.execute().body();

                EventBus.getDefault().post(elements);

            } catch (IOException e) {
                Log.e("TAG", e.getMessage());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(List<Talk> elements) {

        SharedPreferences sharedPreferences = getSharedPreferences();
        for (Talk talk : elements) {
            talk.setFavorited(sharedPreferences.getBoolean(String.valueOf(talk.getId()), false));
        }

        talks.clear();
        talks.addAll(elements);
        adapter.notifyDataSetChanged();
    }
}
