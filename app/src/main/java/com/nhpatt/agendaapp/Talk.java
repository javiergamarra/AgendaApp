package com.nhpatt.agendaapp;

import java.io.Serializable;

public class Talk implements Serializable {

    private final String speaker;
    private final String picture;
    private final String title;
    private final String time;
    private boolean favorited;


    public Talk(String time, String title, String speaker, String picture) {
        this.time = time;
        this.title = title;
        this.favorited = false;
        this.speaker = speaker;
        this.picture = picture;
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
