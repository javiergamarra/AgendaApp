package com.nhpatt.agendaapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class TalkPresenter implements Presenter {

    private MainActivity activity;
    private List<Talk> talks = new ArrayList<>();

    public TalkPresenter(MainActivity activity) {
        this.activity = activity;
    }

    public void start() {
        EventBus.getDefault().register(this);

        requestGPSPermission();

        loadTalks();
    }

    @Override
    public void setActivity(Activity activity) {
        this.activity = (MainActivity) activity;
    }

    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    private void loadTalks() {
        if (talks.isEmpty()) {
            new Thread(new TalkProcessor()).start();
        } else {
            activity.paintTalks(talks);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(List<Talk> elements) {
        this.talks = elements;

        SharedPreferences sharedPreferences = ((MyAgendaApp) activity.getApplicationContext()).getSharedPreferences();
        for (Talk talk : elements) {
            talk.setFavorited(sharedPreferences.getBoolean(String.valueOf(talk.getId()), false));
        }
        activity.paintTalks(elements);
    }

    public void favoriteTalk(Talk talk) {
        talk.setFavorited(!talk.isFavorited());

        ((MyAgendaApp) activity.getApplicationContext()).getSharedPreferences().edit()
                .putBoolean(String.valueOf(talk.getId()), talk.isFavorited()).apply();
    }

    private void requestGPSPermission() {
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
                        @SuppressLint("MissingPermission")
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        System.out.println(location);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
}
