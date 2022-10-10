package com.c1ph3r.c1ph3rbank.controller;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.View;

import com.c1ph3r.c1ph3rbank.DashBoard;
import com.c1ph3r.c1ph3rbank.MainActivity;
import com.c1ph3r.c1ph3rbank.R;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class UserVerification {
    private MainActivity mainActivity;
    UserDataBase userData;
    TextInputEditText userName, pin;
    TextInputLayout pinLayout, userNameLayout;


    public UserVerification(MainActivity mainActivity,TextInputEditText userName,TextInputEditText pin,TextInputLayout pinLayout,TextInputLayout userNameLayout ) {
        this.mainActivity = mainActivity;
        this.userName = userName;
        this.pin = pin;
        this.pinLayout = pinLayout;
        this.userNameLayout = userNameLayout;
    }

    public void verifyTheUser() {
        boolean userVerified = false;
        for (int i = 0; i < mainActivity.userDetail.userDataBase.size(); i++) {
            if (String.valueOf(userName.getText()).equals(mainActivity.userDetail.userDataBase.get(i).getName())) {
                if (String.valueOf(pin.getText()).equals(String.valueOf(mainActivity.userDetail.userDataBase.get(i).getPin()))) {
                    Intent intent = new Intent(mainActivity, DashBoard.class);
                    userData = mainActivity.userDetail.userDataBase.get(i);
                    intent.putExtra("value", userData);
                    mainActivity.startActivity(intent);
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
