package com.liferay.agenda.agendaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView listView = (ListView) findViewById(R.id.list);
		List<Event> events = new ArrayList<>();
		events.add(new Event("09:00", "Android Workshop"));
		events.add(new Event("09:30", "SemVer"));
		EventAdapter adapter = new EventAdapter(this, R.layout.event_row, events);
		listView.setAdapter(adapter);
	}
}
