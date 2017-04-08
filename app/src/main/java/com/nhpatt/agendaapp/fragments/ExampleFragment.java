package com.nhpatt.agendaapp.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nhpatt.agendaapp.R;

public class ExampleFragment extends Fragment implements TextWatcher {

    private Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

        TextInputLayout name = (TextInputLayout) view.findViewById(R.id.name_edit);
        name.getEditText().addTextChangedListener(this);
        submit = (Button) view.findViewById(R.id.submit_button);

        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        submit.setEnabled(!s.toString().isEmpty());
    }

}