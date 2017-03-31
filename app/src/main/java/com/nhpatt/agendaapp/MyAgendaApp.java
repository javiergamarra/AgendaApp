package com.nhpatt.agendaapp;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nhpatt.agendaapp.sql.DBHelper;
import com.squareup.leakcanary.LeakCanary;

public class MyAgendaApp extends Application {

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

        storeAndRead();
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
}
