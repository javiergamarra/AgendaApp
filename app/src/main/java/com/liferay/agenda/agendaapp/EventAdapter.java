package com.liferay.agenda.agendaapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

class EventAdapter extends ArrayAdapter<Event> implements View.OnClickListener {

	public EventAdapter(MainActivity mainActivity, int row, List<Event> events) {
		super(mainActivity, row, events);
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_row, parent, false);
		}
		TextView hour = (TextView) convertView.findViewById(R.id.event_hour);
		Event event = getItem(position);
		hour.setText(event.getHour());

		TextView text = (TextView) convertView.findViewById(R.id.event_text);
		text.setText(event.getName());

		ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.image_button);

		Picasso.with(getContext()).load("http://i.imgur.com/DvpvklR.png").resize(80, 80).into(imageButton);
		imageButton.setOnClickListener(this);

		return convertView;
	}

	@Override
	public void onClick(View v) {
		getContext().startActivity(new Intent(getContext(), DetailActivity.class));
	}
}
