package com.nhpatt.agendaapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.TalkViewHolder> {

    private final List<Talk> talks;

    public TalkAdapter(List<Talk> talks) {
        this.talks = talks;
    }

    @Override
    public TalkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new TalkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TalkViewHolder holder, int position) {
        holder.paint(talks.get(position));
    }

    @Override
    public int getItemCount() {
        return talks.size();
    }

    public class TalkViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView time;

        public TalkViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            time = (TextView) itemView.findViewById(R.id.time);
        }

        public void paint(Talk talk) {
            title.setText(talk.getTitle());
            time.setText(talk.getTime());
        }
    }
}
