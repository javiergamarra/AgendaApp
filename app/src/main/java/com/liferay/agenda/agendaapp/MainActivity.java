package com.liferay.agenda.agendaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private boolean state;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//ImageButton imageButton = (ImageButton) findViewById(R.id.image_button);
		//imageButton.setOnClickListener(this);
		//
		//View row = findViewById(R.id.row);
		//row.setOnClickListener(this);

		ListView listView = (ListView) findViewById(R.id.list);
		String[] list = new String[] { "uno", "dos" };
		listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list));
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
