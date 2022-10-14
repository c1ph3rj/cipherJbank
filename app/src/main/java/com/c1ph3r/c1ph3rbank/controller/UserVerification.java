package com.c1ph3r.c1ph3rbank.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Toast;

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

    public void dataRequiredForVerification(MainActivity mainActivity,TextInputEditText userName,TextInputEditText pin,TextInputLayout pinLayout,TextInputLayout userNameLayout ) {
        this.mainActivity = mainActivity;
        this.userName = userName;
        this.pin = pin;
        this.pinLayout = pinLayout;
        this.userNameLayout = userNameLayout;
    }

    // QUERY PART START

    public void verifyTheUser( SQLiteDatabase userDB, TextInputEditText userName, TextInputEditText pin) {
        Cursor matchUserName = userDB.rawQuery("SELECT userName FROM userDetails WHERE userName = ?",new String[]{String.valueOf(userName.getText())} );
        Cursor pinMatch = userDB.rawQuery("SELECT userName , pin FROM userDetails WHERE userName =? AND pin = ?",new String[]{String.valueOf(userName.getText()),String.valueOf(pin.getText())} );
        matchUserName.moveToFirst();
        if(matchUserName.getCount() == 1 && matchUserName.getString(0).equals(String.valueOf(userName.getText()))) {
            pinMatch.moveToFirst();
            if(pinMatch.getCount() == 1 && ((pinMatch.getString(0).equals(String.valueOf(userName.getText()))) &&pinMatch.getString(1).equals(String.valueOf(pin.getText())))) {
                // Verified using query.
                System.out.println((pinMatch.getString(0) + pinMatch.getString(1)));
                System.out.println(pinMatch.getPosition());
            }
        }
        pinMatch.close();
        matchUserName.close();

    }

    // QUERY PART END

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

    public void verifyTheUser(ArrayList<UserDataBase> userDataBase) {
        boolean userVerified = false;
        for (int i = 0; i < userDataBase.size(); i++) {
            if (String.valueOf(userName.getText()).equals(userDataBase.get(i).getName())) {
                if (String.valueOf(pin.getText()).equals(String.valueOf(userDataBase.get(i).getPin()))) {
                    userData = userDataBase.get(i);
                    SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("IndexValue", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editValues = sharedPreferences.edit();
                    editValues.putInt("value",userDataBase.indexOf(userDataBase.get(i)));
                    editValues.apply();
                    userData.setLoggedIn(true);
                    UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(mainActivity);
                    UserDetail userDetail = new UserDetail();
                    userDetail.updateUserData(userDataBase.indexOf(userDataBase.get(i)), userData.getBalance(),userDataBaseHelper, userData.isLoggedIn() );
                    Intent intent = new Intent(mainActivity, DashBoard.class);
                    mainActivity.startActivity(intent);
                    mainActivity.finish();
                }else{
                    pinLayout.setError(mainActivity.getString(R.string.ErrorInvalidPin));
                    pinLayout.setBoxStrokeColor(mainActivity.getColor(R.color.ErrorRed));
                    pinLayout.setErrorTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
                    pinLayout.setHintTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
                    pinLayout.setErrorIconTintList(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
                }
                userVerified = true;
                break;
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
}
