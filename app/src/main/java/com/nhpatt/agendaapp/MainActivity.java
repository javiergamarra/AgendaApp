package com.nhpatt.agendaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ButterKnife.bind(this);

        adapter = new TalkAdapter(this, new ArrayList<>(), this);
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
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.fab)
    public void fabClicked() {
        Snackbar.make(findViewById(R.id.main_content), "FAB clicked!", Snackbar.LENGTH_SHORT).show();
    }

    public void paintTalks(List<Talk> elements) {
        adapter.swapTalks(elements);
    }
}
