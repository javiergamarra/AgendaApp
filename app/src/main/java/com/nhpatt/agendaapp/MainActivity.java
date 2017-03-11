package com.nhpatt.agendaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final Talk TALK_1 = new Talk("09:00", "Reactive Programming and RxJava2", "Jorge Barroso", "https://pbs.twimg.com/profile_images/768819154157731840/4vPCyGBh.jpg");
    private static final Talk TALK_2 = new Talk("10:00", "Workshop of Android Programming", "Jorge Barroso", "https://pbs.twimg.com/profile_images/768819154157731840/4vPCyGBh.jpg");

    private static List<Talk> talks;

    private TalkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadTalks();
    }

    private void loadTalks() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://data.agenda.wedeploy.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TalksService service = retrofit.create(TalksService.class);

        Call<List<Talk>> call = service.allTalks();
        call.enqueue(new Callback<List<Talk>>() {
            @Override
            public void onResponse(Call<List<Talk>> call, Response<List<Talk>> response) {
                talks = response.body();
                RecyclerView listView = (RecyclerView) findViewById(R.id.list);
                listView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter = new TalkAdapter(MainActivity.this, talks, MainActivity.this);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Talk>> call, Throwable t) {

            }
        });
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
            adapter.notifyDataSetChanged();
        }
    }
}
