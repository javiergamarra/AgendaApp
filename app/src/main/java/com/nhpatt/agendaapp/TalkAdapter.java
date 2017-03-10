package com.nhpatt.agendaapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.TalkViewHolder> {

    private final List<Talk> talks;
    private final View.OnClickListener onClickListener;
    private Context context;

    public TalkAdapter(Context context, List<Talk> talks, View.OnClickListener onClickListener) {
        this.talks = talks;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public TalkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new TalkViewHolder(view, onClickListener);
    }

    @Override
    public void onBindViewHolder(TalkViewHolder holder, int position) {
        holder.paint(talks.get(position));
    }

    @Override
    public int getItemCount() {
        return talks.size();
    }

    public class TalkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView title;
        private final TextView time;
        private final ImageView favoriteImage;
        private final View.OnClickListener onClickListener;

        public TalkViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            this.onClickListener = onClickListener;

            title = (TextView) itemView.findViewById(R.id.title);
            time = (TextView) itemView.findViewById(R.id.time);
            favoriteImage = (ImageView) itemView.findViewById(R.id.favorite_image);
            favoriteImage.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary));
            favoriteImage.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void paint(Talk talk) {
            title.setText(talk.getTitle());
            time.setText(talk.getTime());

            int id = talk.isFavorited() ? R.drawable.favorite : R.drawable.favorite_border;
            favoriteImage.setImageDrawable(ContextCompat.getDrawable(context, id));
        }

        @Override
        public void onClick(View v) {
            v.setTag(getAdapterPosition());
            onClickListener.onClick(v);
        }
    }
}
