package com.nhpatt.agendaapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import com.nhpatt.agendaapp.fragments.ExampleFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AbstractActivityWithPresenter<TalkPresenter> implements View.OnClickListener {

    @BindView(R.id.list)
    RecyclerView listView;
    private TalkAdapter adapter;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ButterKnife.bind(this);

        adapter = new TalkAdapter(this, new ArrayList<>(), this);
        adapter.setHasStableIds(true);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new ExampleFragment()).commit();
    }

    @Override
    protected TalkPresenter createPresenter(AbstractActivityWithPresenter<TalkPresenter> activity) {
        return new TalkPresenter((MainActivity) activity);
    }

    @Override
    public void onClick(View v) {

        Talk talk = adapter.getTalks((Integer) v.getTag());

        if (v.getId() == R.id.row) {
            Intent intent = new Intent(this, DetailTalkActivity.class);
            intent.putExtra("talk", talk);
            startActivity(intent);
        } else {
            getPresenter().favoriteTalk(talk);

            Talk newTalk = new Talk("09:00", "Talk", "nhpatt", "");

            if (talk.isFavorited()) {
                adapter.getTalks().add(newTalk);
            } else {
                adapter.getTalks().remove(0);
            }
            adapter.notifyDataSetChanged();

            ViewGroup viewById = (ViewGroup) findViewById(R.id.main_content);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(viewById);


                View child = viewById.findViewById(R.id.fab);
                child.setVisibility(View.INVISIBLE);
            }
        }
    }

    @OnClick(R.id.fab)
    public void fabClicked() {
        Snackbar.make(findViewById(R.id.main_content), "FAB clicked!", Snackbar.LENGTH_SHORT).show();
    }

    public void paintTalks(List<Talk> elements) {
        adapter.addTalks(elements);
    }
}
