package com.nhpatt.agendaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView favoriteImage = (ImageView) findViewById(R.id.favorite_image);
        favoriteImage.setColorFilter(getResources().getColor(R.color.colorPrimary));

        Log.d("TAG", "Hi!");
    }
}
