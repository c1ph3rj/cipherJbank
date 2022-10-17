package com.c1ph3r.c1ph3rbank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
                // This that need to be done when logout button is clicked.
                Logout.setOnClickListener(view1 -> {
                    MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                    alertDialogBuilder.setTitle(getString(R.string.Quit_String)).setMessage(getString(R.string.Do_you_want_to_logout)).setPositiveButton(getString(R.string.No_String), (dialogInterface, i1) -> {})
                            .setNegativeButton(getString(R.string.Yes_String), (dialogInterface, i1) -> {
                        UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(getActivity());
                        // Getting the index value of the user from the shared preference.
                        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(getString(R.string.IndexValue_Strings), Context.MODE_PRIVATE);
                        int value = sharedPreferences.getInt(getString(R.string.value_String),0);
                        userDataBaseHelper.getUserDataBase();
                        // Changing the logging state if the user press yes.
                        userData = userDataBaseHelper.getUserData(value);
                        userData.setLoggedIn(false);
                        // updating the values to the sqlite DB.
                        userDataBaseHelper.updateUserData(value, userData.getBalance(), userData.isLoggedIn() );
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }).show();
                });

                MaterialButton changePin = view.findViewById(R.id.ChangePin);
                changePin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Coming soon ..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }catch (Exception e){
            System.out.println(e + "| Settings Layout");
        }
    }
}