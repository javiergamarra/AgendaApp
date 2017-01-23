package com.liferay.agenda.agendaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class DetailActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		Event event = (Event) getIntent().getSerializableExtra("event");
		Log.d("Agenda", event.getName());
	}
}
