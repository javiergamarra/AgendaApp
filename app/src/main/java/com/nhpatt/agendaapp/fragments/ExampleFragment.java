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
import android.widget.TextView;

import com.nhpatt.agendaapp.MainActivity;
import com.nhpatt.agendaapp.R;
import com.nhpatt.agendaapp.Talk;
import com.nhpatt.agendaapp.TalkPresenter;

public class ExampleFragment extends Fragment implements TextWatcher, View.OnClickListener {

    private Button submit;
    private TextView name;
    private TextView description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

        name = ((TextInputLayout) view.findViewById(R.id.name_edit)).getEditText();
        name.addTextChangedListener(this);
        description = ((TextInputLayout) view.findViewById(R.id.description_edit)).getEditText();
        submit = (Button) view.findViewById(R.id.submit_button);
        submit.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        ((MainActivity) getActivity()).getPresenter().addTalk(new Talk("", name.getText().toString(), "", ""));

    }

}