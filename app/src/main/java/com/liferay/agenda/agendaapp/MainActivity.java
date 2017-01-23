package com.liferay.agenda.agendaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageButton imageButton = (ImageButton) findViewById(R.id.image_button);
		imageButton.setOnClickListener(this);

		View row = findViewById(R.id.row);
		row.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.row) {
			startActivity(new Intent(this, DetailActivity.class));
		}
	}
}
