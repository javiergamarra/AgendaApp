package com.nhpatt.agendaapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.TalkViewHolder> {

    private final List<String> items;

    public TalkAdapter(List<String> items) {
        this.items = items;
    }

    @Override
    public TalkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new TalkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TalkViewHolder holder, int position) {
        holder.paint(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class TalkViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;

        public TalkViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
        }

        public void paint(String s) {
            name.setText(s);
        }
    }
}
