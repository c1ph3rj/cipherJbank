package com.c1ph3r.c1ph3rbank;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c1ph3r.c1ph3rbank.model.UserDataBase;

public class credit_history extends Fragment {

    public credit_history(UserDataBase userData) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_credit_history, container, false);
    }
}