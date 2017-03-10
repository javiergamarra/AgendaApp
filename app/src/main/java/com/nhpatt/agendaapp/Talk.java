package com.nhpatt.agendaapp;

public class Talk {

    private String title;
    private String time;
    private boolean favorited;


    public Talk(String time, String title) {
        this.time = time;
        this.title = title;
        this.favorited = false;
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
}
