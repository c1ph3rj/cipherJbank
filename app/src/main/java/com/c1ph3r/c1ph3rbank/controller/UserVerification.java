package com.c1ph3r.c1ph3rbank.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.c1ph3r.c1ph3rbank.DashBoard;
import com.c1ph3r.c1ph3rbank.MainActivity;
import com.c1ph3r.c1ph3rbank.R;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.c1ph3r.c1ph3rbank.model.UserDataBaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class UserVerification {
    private MainActivity mainActivity;
    UserDataBase userData;
    TextInputEditText userName, pin;
    TextInputLayout pinLayout, userNameLayout;
    ArrayList<UserDataBase> userDataBase;

    public UserVerification(MainActivity mainActivity,TextInputEditText userName,TextInputEditText pin,TextInputLayout pinLayout,TextInputLayout userNameLayout ) {
        this.mainActivity = mainActivity;
        this.userName = userName;
        this.pin = pin;
        this.pinLayout = pinLayout;
        this.userNameLayout = userNameLayout;
        UserDetail userDetail = new UserDetail(mainActivity);
        this.userDataBase = userDetail.userDataBase;
    }


    public void verifyTheUser() {
        boolean userVerified = false;
        for (int i = 0; i < userDataBase.size(); i++) {
            if (String.valueOf(userName.getText()).equals(userDataBase.get(i).getName())) {
                if (String.valueOf(pin.getText()).equals(String.valueOf(userDataBase.get(i).getPin()))) {
                    Intent intent = new Intent(mainActivity, DashBoard.class);
                    userData = mainActivity.userDetail.userDataBase.get(i);
                    SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("IndexValue",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editValues = sharedPreferences.edit();
                    editValues.putInt("value",userDataBase.indexOf(userDataBase.get(i)));
                    editValues.apply();
                    intent.putExtra("value", userData);
                    mainActivity.startActivity(intent);
                    mainActivity.finish();
                }else{
                    pinLayout.setError("Invalid Pin");
                    pinLayout.setBoxStrokeColor(mainActivity.getColor(R.color.ErrorRed));
                    pinLayout.setErrorTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
                    pinLayout.setHintTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
                    pinLayout.setErrorIconTintList(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
                }
                userVerified = true;
            }
        }if(!userVerified){
            userNameLayout.setError("UserName or Password invalid.");
            userNameLayout.setErrorEnabled(true);
            userNameLayout.setBoxStrokeErrorColor(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
            userNameLayout.setErrorTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
            userNameLayout.setHintTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
            userNameLayout.setErrorIconTintList(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
        }
    }

    public void changeColorOfInputs() {
        pin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                pinLayout.setErrorEnabled(false);
                pinLayout.setHintTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.Indigo)));
                pinLayout.setBoxStrokeColor(mainActivity.getColor(R.color.Indigo));
            }
        });
        userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                userNameLayout.setErrorEnabled(false);
                userNameLayout.setHintTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.Indigo)));
                userNameLayout.setBoxStrokeColor(mainActivity.getColor(R.color.Indigo));
            }
        });
    }

}
