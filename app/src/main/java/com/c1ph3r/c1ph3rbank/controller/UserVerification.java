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
import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.c1ph3r.c1ph3rbank.model.UserDataBaseHelper;
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
     // it verifies the user credentials.
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


    // Codes now running on these method.
     // used to verify the userName and password using the model class.
     // if the user Name and password it wrong the colors of the layout will be changed.
     // the color of the field will remains the same until user focus the field.
    public void verifyTheUser(ArrayList<UserDataBase> userDataBase) {
        try{
            boolean userVerified = false;
            // Verifying userName and password using for Loop and model class.
            for (int i = 0; i < userDataBase.size(); i++) {
                // Verifying the userName field.
                if (String.valueOf(userName.getText()).equals(userDataBase.get(i).getName())) {
                    // Converting and checking the pin field if the user enters 0000 it will be changed
                    // These condition will manage the error.
                    String pinValidate = String.valueOf(userDataBase.get(i).getPin());
                    pinValidate = (pinValidate.length()==3)? "0" + pinValidate:(pinValidate.length()==2)?"00"+pinValidate:(pinValidate.length()==1)?"000"+pinValidate:pinValidate;
                    // Verifying the pin.
                    if (String.valueOf(pin.getText()).equals(pinValidate)) {
                        userData = userDataBase.get(i);
                        // Using shared preference to send the index value of the user stored in the model class.
                        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("IndexValue", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editValues = sharedPreferences.edit();
                        editValues.putInt("value",userDataBase.indexOf(userDataBase.get(i)));
                        editValues.apply();
                        // Saving the user state for user friendly application.
                        userData.setLoggedIn(true);
                        UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(mainActivity);
                        UserDetail userDetail = new UserDetail();
                        userDetail.updateUserData(userDataBase.indexOf(userDataBase.get(i)), userData.getBalance(),userDataBaseHelper, userData.isLoggedIn() );
                        // Calling the dashboard fragment.
                        Intent intent = new Intent(mainActivity, DashBoard.class);
                        mainActivity.startActivity(intent);
                        // Killing the main activity.
                        mainActivity.finish();
                    }else{
                        // If the pin is wrong it display the error message in pin field.
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
                // If the user is not verified this part will execute.
                userNameLayout.setError(mainActivity.getString(R.string.userNameAndPasswordError));
                userNameLayout.setErrorEnabled(true);
                userNameLayout.setBoxStrokeErrorColor(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
                userNameLayout.setErrorTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
                userNameLayout.setHintTextColor(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
                userNameLayout.setErrorIconTintList(ColorStateList.valueOf(mainActivity.getColor(R.color.ErrorRed)));
            }

        }catch(Exception e){
            System.out.println(e+" | verify the user.");
        }
    }
}
