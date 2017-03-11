package com.nhpatt.agendaapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final Talk TALK_1 = new Talk("09:00", "Reactive Programming and RxJava2", "Jorge Barroso", "https://pbs.twimg.com/profile_images/768819154157731840/4vPCyGBh.jpg");
    private static final Talk TALK_2 = new Talk("10:00", "Workshop of Android Programming", "Jorge Barroso", "https://pbs.twimg.com/profile_images/768819154157731840/4vPCyGBh.jpg");

    private static List<Talk> talks;

    private static TalkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadTalks();
    }

    private void loadTalks() {
        new Thread(new TalkProcessor(new WeakReference<>(this))).start();
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

            getSharedPreferences()
                    .edit()
                    .putBoolean(String.valueOf(talk.getId()), talk.isFavorited())
                    .commit();

            adapter.notifyDataSetChanged();
        }
    }

    private SharedPreferences getSharedPreferences() {
        return getSharedPreferences("AgendaApp", Context.MODE_PRIVATE);
    }

    private static class TalkProcessor implements Runnable {
        private final WeakReference<MainActivity> activityWeakReference;

        public TalkProcessor(WeakReference<MainActivity> activityWeakReference) {
            this.activityWeakReference = activityWeakReference;
        }

        public void run() {
            try {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://data.agenda.wedeploy.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TalksService service = retrofit.create(TalksService.class);

                final Call<List<Talk>> call = service.allTalks();
                List<Talk> elements = call.execute().body();

                final MainActivity activity = activityWeakReference.get();
                if (activity != null) {

                    SharedPreferences sharedPreferences = activity.getSharedPreferences();
                    for (Talk talk : elements) {
                        talk.setFavorited(sharedPreferences.getBoolean(String.valueOf(talk.getId()), false));
                    }

                    talks = elements;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RecyclerView listView = (RecyclerView) activity.findViewById(R.id.list);
                            listView.setLayoutManager(new LinearLayoutManager(activity));
                            adapter = new TalkAdapter(activity, talks, activity);
                            listView.setAdapter(adapter);
                        }
                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
