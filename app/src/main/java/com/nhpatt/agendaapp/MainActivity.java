package com.nhpatt.agendaapp;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView favoriteImage = (ImageView) findViewById(R.id.favorite_image);
        favoriteImage.setColorFilter(getResources().getColor(R.color.colorPrimary));
        favoriteImage.setOnClickListener(this);

        Log.d("TAG", "Hi!");
    }

    @Override
    public void onClick(View v) {
        ImageView favoriteImage = (ImageView) v;
        favorite = !favorite;
        int id = favorite ? R.drawable.favorite : R.drawable.favorite_border;
        favoriteImage.setImageDrawable(ContextCompat.getDrawable(this, id));

        if (favorite) {
            Toast.makeText(this, "Favorite!", Toast.LENGTH_SHORT).show();
        }

    }

}
