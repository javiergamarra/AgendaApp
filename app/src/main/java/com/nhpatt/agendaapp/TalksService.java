package com.nhpatt.agendaapp;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TalksService {

    @GET("/talks")
    Call<List<Talk>> allTalks();

    @POST("/talks")
    Call<JsonObject> addTalk(@Body Talk talk);

    @DELETE("/talks")
    Call<String> deleteTalks();
}
