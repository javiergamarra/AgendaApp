package com.liferay.agenda.agendaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private boolean state;

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
	protected void onResume() {
		super.onResume();

		Log.d("Agenda", "State: " + state);
	}

	@Override
	public void onClick(View v) {
		state = true;
		if (v.getId() == R.id.row) {
			startActivity(new Intent(this, DetailActivity.class));
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("state", state);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		state = savedInstanceState.getBoolean("state");
	}
}
