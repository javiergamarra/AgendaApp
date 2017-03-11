package com.nhpatt.agendaapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailTalkActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AgendaApp";
    private TextView speakerBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_talk);

        Talk talk = (Talk) getIntent().getSerializableExtra("talk");

        TextView title = (TextView) findViewById(R.id.talk_title);
        title.setText(talk.getTitle());

        TextView time = (TextView) findViewById(R.id.talk_time);
        time.setText(talk.getTime());

        TextView speaker = (TextView) findViewById(R.id.speaker_name);
        speaker.setText(talk.getSpeaker());

        speakerBio = (TextView) findViewById(R.id.speaker_bio);

        Picasso.with(this)
                .load(talk.getPicture())
                .resize(200, 200)
                .into((ImageView) findViewById(R.id.speaker_image));

        loadLastTweet();

        Button addPhotograph = (Button) findViewById(R.id.add_photo);
        addPhotograph.setOnClickListener(this);
    }

    private void loadLastTweet() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://twitter.com/flipper83/")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bio = parse(response.body().string());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        speakerBio.setText(Html.fromHtml(bio));
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
            }
        });
    }

    private String parse(String string) {
        int start = string.indexOf(">", string.indexOf("ProfileHeaderCard-bio u-dir"));
        int end = string.indexOf("ProfileHeaderCard-location");

        return string.substring(start + 1, end);
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