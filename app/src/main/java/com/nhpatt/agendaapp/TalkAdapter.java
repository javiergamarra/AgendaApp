package com.nhpatt.agendaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TalkAdapter extends ArrayAdapter<String> {

    public TalkAdapter(Context context, String[] elements) {
        super(context, R.layout.row, elements);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(getItem(position));


        return convertView;
    }
}
