package com.nhpatt.agendaapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailTalkActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_talk);

        Talk talk = (Talk) getIntent().getSerializableExtra("talk");

        TextView title = (TextView) findViewById(R.id.talk_title);
        title.setText(talk.getTitle());

        TextView time = (TextView) findViewById(R.id.talk_time);
        time.setText(talk.getTime());

        Button addPhotograph = (Button) findViewById(R.id.add_photo);
        addPhotograph.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");

        ImageView imageView = (ImageView) findViewById(R.id.photo_taken);
        imageView.setImageBitmap(imageBitmap);
    }

}