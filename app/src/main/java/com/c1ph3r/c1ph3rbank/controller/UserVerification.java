package com.c1ph3r.c1ph3rbank.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.c1ph3r.c1ph3rbank.DashBoard;
import com.c1ph3r.c1ph3rbank.MainActivity;
import com.c1ph3r.c1ph3rbank.R;
import com.c1ph3r.c1ph3rbank.Model.UserDataBase;
import com.c1ph3r.c1ph3rbank.DBHelper.UserDataBaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
 // Controller Class For user verification.
public class UserVerification {
    private MainActivity mainActivity;
    UserDataBase userData;
    TextInputEditText userName, pin;
    TextInputLayout pinLayout, userNameLayout;

    // Used for initializing the data required for the controller class.
    public void dataRequiredForVerification(MainActivity mainActivity,TextInputEditText userName,TextInputEditText pin,TextInputLayout pinLayout,TextInputLayout userNameLayout ) {
        this.mainActivity = mainActivity;
        this.userName = userName;
        this.pin = pin;
        this.pinLayout = pinLayout;
        this.userNameLayout = userNameLayout;
    }

    // QUERY PART START
    // its working
    // Codes now running on these method.
    // used to verify the userName and password using the DBHelper class.
    // if the user Name and password it wrong the colors of the layout will be changed.
    // the color of the field will remains the same until user focus the field.
     // it verifies the user credentials.
    public void verifyTheUser(ArrayList<UserDataBase> userDataBase, UserDataBaseHelper userDB, TextInputEditText userName, TextInputEditText pin) {
        int value = userDB.isUserPresentInDB(userName, pin);
        if (value == -1) {
            pinLayoutChangeColor();
        } else if (value == -2) {
            userNameChangeColor();
        }else{
            storeData(userDataBase, value);
        }
    }

    // QUERY PART END


     // To Change the color of the input fields after user focused.
    public void changeColorOfInputs() {
        // this is the pin part.
        pin.setOnFocusChangeListener((view, b) -> {
            pinLayout.setErrorEnabled(false);
            pinLayout.setHintTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.Indigo)));
            pinLayout.setBoxStrokeColor(mainActivity.getColor(R.color.Indigo));
        });
        // this is for user Name part.
        userName.setOnFocusChangeListener((view, b) -> {
            userNameLayout.setErrorEnabled(false);
            userNameLayout.setHintTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.Indigo)));
            userNameLayout.setBoxStrokeColor(mainActivity.getColor(R.color.Indigo));
        });
    }

    void pinLayoutChangeColor(){
        // If the pin is wrong it display the error message in pin field.
        pinLayout.setError(mainActivity.getString(R.string.ErrorInvalidPin));
        pinLayout.setBoxStrokeColor(mainActivity.getColor(R.color.ErrorRed));
        pinLayout.setErrorTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
        pinLayout.setHintTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
        pinLayout.setErrorIconTintList(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
    }


    void userNameChangeColor(){
        // If the user is not verified this part will execute.
        userNameLayout.setError(mainActivity.getString(R.string.userNameAndPasswordError));
        userNameLayout.setErrorEnabled(true);
        userNameLayout.setBoxStrokeErrorColor(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
        userNameLayout.setErrorTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
        userNameLayout.setHintTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
        userNameLayout.setErrorIconTintList(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
    }

   void storeData(ArrayList<UserDataBase> userDataBase, int i){
       userData = userDataBase.get(i);
       System.out.println("Hello");
       // Using shared preference to send the index value of the user stored in the DBHelper class.
       SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("IndexValue", Context.MODE_PRIVATE);
       SharedPreferences.Editor editValues = sharedPreferences.edit();
       editValues.putInt("value",userDataBase.indexOf(userDataBase.get(i)));
       editValues.apply();
       // Saving the user state for user friendly application.
       userData.setLoggedIn(true);
       UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(mainActivity);
       userDataBaseHelper.updateUserData(userDataBase.indexOf(userDataBase.get(i)), userData.getBalance(), userData.isLoggedIn() );
       // Calling the dashboard fragment.
       Intent intent = new Intent(mainActivity, DashBoard.class);
       mainActivity.startActivity(intent);
       // Killing the main activity.
       mainActivity.finish();
    }

}
