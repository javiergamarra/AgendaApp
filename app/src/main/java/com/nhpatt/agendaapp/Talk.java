package com.nhpatt.agendaapp;

public class Talk {

    private String title;
    private String time;

    public Talk(String time, String title) {
        this.time = time;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }
}
