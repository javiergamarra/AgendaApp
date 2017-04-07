package com.nhpatt.agendaapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhpatt.agendaapp.R;

public class ExampleFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

        view.setX(-2000);
        view.setAlpha(0.5f);

        view.animate().setDuration(2000).alpha(1f).xBy(2030).start();

        return view;
    }
}