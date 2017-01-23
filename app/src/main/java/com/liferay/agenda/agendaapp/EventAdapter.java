package com.liferay.agenda.agendaapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

class EventAdapter extends ArrayAdapter<Event> {
	public EventAdapter(MainActivity mainActivity, int row, List<Event> list) {
		super(mainActivity, row, list);
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
		}
		TextView hour = (TextView) convertView.findViewById(R.id.hour);
		Event event = getItem(position);
		hour.setText(event.getHour());

		TextView text = (TextView) convertView.findViewById(R.id.text);
		text.setText(event.getName());
		return convertView;
	}
}
