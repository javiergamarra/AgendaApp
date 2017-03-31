package com.nhpatt.agendaapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class Talk extends RealmObject implements Serializable {

    private Long id;
    @SerializedName("speaker")
    private String speaker;
    private String picture;
    private String title;
    private String time;
    private boolean favorited;

    public Talk() {
        super();
    }

    public Talk(String time, String title, String speaker, String picture) {
        this.time = time;
        this.title = title;
        this.favorited = false;
        this.speaker = speaker;
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getPicture() {
        return picture;
    }
}
