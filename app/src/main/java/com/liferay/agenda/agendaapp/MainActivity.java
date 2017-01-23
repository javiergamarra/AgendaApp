package com.liferay.agenda.agendaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageButton imageButton = (ImageButton) findViewById(R.id.image_button);
		imageButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
	}
}
