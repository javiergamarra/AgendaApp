package com.nhpatt.agendaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class AbstractActivityWithPresenter<T extends Presenter> extends AppCompatActivity {

    private T presenter;

    @Override
    protected void onStart() {
        super.onStart();

        getPresenter().start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        getPresenter().stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = getLastNonConfigurationInstance() == null ?
                createPresenter(this) :
                (T) getLastCustomNonConfigurationInstance();
        presenter.setActivity(this);
    }

    protected abstract T createPresenter(AbstractActivityWithPresenter<T> activity);

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return getPresenter();
    }

    public T getPresenter() {
        return presenter;
    }
}
