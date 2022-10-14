package com.c1ph3r.c1ph3rbank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c1ph3r.c1ph3rbank.controller.UserDetail;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.c1ph3r.c1ph3rbank.model.UserDataBaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SettingsLayout extends Fragment {
    UserDataBase userData;
    public SettingsLayout(UserDataBase userData) {
        this.userData = userData;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settings_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        MaterialButton Logout = view.findViewById(R.id.Logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                alertDialogBuilder.setTitle("Quit").setMessage("Do you Want to Logout?").setPositiveButton("Back", (dialogInterface, i1) -> {}).setNegativeButton("Exit", (dialogInterface, i1) -> {
                    UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(getActivity());
                    UserDetail userDetail = new UserDetail();
                    SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("IndexValue", Context.MODE_PRIVATE);
                    int value = sharedPreferences.getInt("value",0);
                    userDetail.getUserDataBase(userDataBaseHelper);
                    userData = userDetail.getUserData(value);
                    userData.setLoggedIn(false);
                    userDetail.updateUserData(value, userData.getBalance(),userDataBaseHelper, userData.isLoggedIn() );
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }).show();
            }
        });
    }
}