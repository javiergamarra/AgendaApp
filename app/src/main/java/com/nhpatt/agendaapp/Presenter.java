package com.nhpatt.agendaapp;

import android.app.Activity;

interface Presenter {
    void stop();

    void start();

    void setActivity(Activity activity);
}
