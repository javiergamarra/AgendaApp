package com.nhpatt.agendaapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.nhpatt.agendaapp.fragments.ExampleFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AbstractActivityWithPresenter<TalkPresenter> implements View.OnClickListener, IdlingResource {

    @BindView(R.id.list)
    RecyclerView listView;
    private TalkAdapter adapter;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private ResourceCallback callback;

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

        ExampleFragment fragment = new ExampleFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment).commit();
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                reveal(findViewById(R.id.main_content));
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void reveal(View view) {

        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        float finalRadius = (float) Math.hypot(cx, cy);

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

        view.setVisibility(View.VISIBLE);
        anim.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void hide(View view) {
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        float initialRadius = (float) Math.hypot(cx, cy);

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });

        anim.start();
    }

    @Override
    public String getName() {
        return MainActivity.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        if (getPresenter().isIdle() && callback != null) {
            callback.onTransitionToIdle();
        }
        return getPresenter().isIdle();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }
}
