package com.nhpatt.agendaapp;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TalksInteractor {

    private Retrofit retrofit = new Retrofit.Builder().baseUrl("http://data.agenda.wedeploy.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private TalksService talksService = retrofit.create(TalksService.class);

    public void listTalks() {
        new Thread(
                () -> {
                    try {
                        final Call<List<Talk>> call = talksService.allTalks();
                        List<Talk> talks = call.execute().body();
                        EventBus.getDefault().post(talks);

                    } catch (IOException e) {
                        Log.e(MyAgendaApp.TAG, e.getMessage());
                    }
                }).start();
    }

    public void addTalk(final Talk talk) {
        new Thread(
                () -> {
                    try {
                        final Call<Void> call = talksService.addTalk(talk);
                        Response<Void> response = call.execute();

                        if (response.code() == 200) {
                            EventBus.getDefault().post(talk);
                        }

                    } catch (IOException e) {
                        Log.e(MyAgendaApp.TAG, e.getMessage());
                    }
                }).start();
    }
}
