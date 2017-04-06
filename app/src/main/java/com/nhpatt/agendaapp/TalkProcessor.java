package com.nhpatt.agendaapp;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TalkProcessor implements Runnable {

    public void run() {
        try {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://data.agenda.wedeploy.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TalksService service = retrofit.create(TalksService.class);

            final Call<List<Talk>> call = service.allTalks();
            List<Talk> talks = call.execute().body();

            EventBus.getDefault().post(talks);

        } catch (IOException e) {
            Log.e(MyAgendaApp.TAG, e.getMessage());
        }
    }
}
