package com.nhpatt.agendaapp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.nhpatt.agendaapp.sql.DBHelper;
import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAgendaApp extends Application implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient apiClient;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(MainActivity.TAG, "Creating app...");

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

//        storeAndRead();
//        storeAndReadWithRealm();

        requestGPSUpdates();
    }

    private void requestGPSUpdates() {
        apiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        apiClient.connect();
    }

    private void storeAndReadWithRealm() {

        Realm.init(this);

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        Talk talk = new Talk("09:00", "title", "speaker", "");
        realm.copyToRealm(talk);
        realm.commitTransaction();

        final RealmResults<Talk> talks = realm.where(Talk.class).findAll();
        System.out.println(talks);
    }

    private void storeAndRead() {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        writableDatabase.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", 0);
        contentValues.put("FAVORITED", 0);
        contentValues.put("NAME", "A");
        writableDatabase.insert("TALKS", "", contentValues);
        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();
        writableDatabase.close();

        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        Cursor talks = readableDatabase.query("TALKS", null, null, null, null, null, null);

        while (talks.moveToNext()) {
            System.out.println(talks.getInt(0));
        }
        readableDatabase.close();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(apiClient);
        Log.d(MainActivity.TAG, String.valueOf(location));

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setFastestInterval(1000);
        locationRequest.setNumUpdates(3);
        locationRequest.setInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(MainActivity.TAG, String.valueOf(location));
    }
}
