package com.c1ph3r.c1ph3rbank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.c1ph3r.c1ph3rbank.model.UserDataBaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SettingsLayout extends Fragment {
    UserDataBase userData;
    // Getting the user Data through the constructor.
    public SettingsLayout(UserDataBase userData) {
        this.userData = userData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Empty constructor to initiate this fragment.
    public SettingsLayout(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_layout, container, false);
    }

    // Executes when the fragment is started.
    @Override
    public void onStart() {
        super.onStart();
        try{
            View view = getView();
            if(view!=null){
                MaterialButton Logout = view.findViewById(R.id.Logout);
                Logout.setOnClickListener(view1 -> {
                    MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    alertDialogBuilder.setTitle("Quit").setMessage("Do you Want to Logout?").setPositiveButton("No", (dialogInterface, i1) -> {}).setNegativeButton("Yes", (dialogInterface, i1) -> {
                        UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(getActivity());
                        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("IndexValue", Context.MODE_PRIVATE);
                        int value = sharedPreferences.getInt("value",0);
                        userDataBaseHelper.getUserDataBase();
                        userData = userDataBaseHelper.getUserData(value);
                        userData.setLoggedIn(false);
                        userDataBaseHelper.updateUserData(value, userData.getBalance(), userData.isLoggedIn() );
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }).show();
                });
            }
        }catch (Exception e){
            System.out.println(e + "| Settings Layout");
        }
    }
}