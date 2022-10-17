package com.c1ph3r.c1ph3rbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.c1ph3r.c1ph3rbank.controller.UserVerification;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.c1ph3r.c1ph3rbank.model.UserDataBaseHelper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


/*TODO :
    -- Primary --
    Verify if the username is available or not --done
    Verify userName and password using the SQLite queries -- half done
    Add transactions page --done
    Handle errors using try catch --done
    Convert strings to string resource file --done
    Clean up code finally --done
    Add comments --done
    -- Secondary --
    Add is user logged in feature --done
    Add change pin option
    Add splash screen effect if possible and utilize the time to verify is user Logged in
    Clean the ui --done
    Add logout feature --done
*/


public class MainActivity extends AppCompatActivity {
    UserDataBaseHelper userDataBaseHelper;
    UserVerification userVerification;
    TextInputEditText userName, pin;
    TextInputLayout pinLayout, userNameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.UserNameField);
        pinLayout = findViewById(R.id.PinLayout);
        userNameLayout = findViewById(R.id.UserNameLayout);
        pin = findViewById(R.id.PinField);

        // To get the user Details from the user Database.
        userDataBaseHelper = new UserDataBaseHelper(this);
        userDataBaseHelper.getUserDataBase();
        userVerification = new UserVerification();
        userVerification.dataRequiredForVerification(MainActivity.this,userName,pin,pinLayout,userNameLayout);
        userVerification.changeColorOfInputs();

        // To check the user db any user is logged in or not.
        for(int i =0;i<userDataBaseHelper.userDataBase.size();i++){
            if(userDataBaseHelper.userDataBase.get(i).isLoggedIn()){
                Intent intent = new Intent(this, DashBoard.class);
                // if logged in store the value of the index in shared preference.
                SharedPreferences sharedPreferences = this.getSharedPreferences("IndexValue", Context.MODE_PRIVATE);
                SharedPreferences.Editor editValues = sharedPreferences.edit();
                editValues.putInt("value",userDataBaseHelper.userDataBase.indexOf(userDataBaseHelper.userDataBase.get(i)));
                editValues.apply();
                startActivity(intent);
                finish();
            }
        }

    }

    // On click Login button to verify the user.
    public void onClickLoginBtn(View view) {
        userDataBaseHelper = new UserDataBaseHelper(this);
        userDataBaseHelper.getUserDataBase();
        ArrayList<UserDataBase> userDataBase = userDataBaseHelper.userDataBase;
        userVerification.verifyTheUser(userDataBase);
        // Query part
        SQLiteDatabase userDB = userDataBaseHelper.getReadableDatabase();
        userVerification.verifyTheUser(userDB, userName, pin);
        //Query part


    }

    // Changing the color when focused
    public void OnClickPinLayout(View view) {
        pinLayout.setErrorEnabled(false);
        pinLayout.setHintTextColor(ColorStateList.valueOf(getColor(R.color.Indigo)));
        pinLayout.setBoxStrokeColor(getColor(R.color.Indigo));
    }

    public void OnClickUserLayout(View view) {
        userNameLayout.setErrorEnabled(false);
        userNameLayout.setHintTextColor(ColorStateList.valueOf(getColor(R.color.Indigo)));
        userNameLayout.setBoxStrokeColor(getColor(R.color.Indigo));
    }


    // On clicking new user It Forwards you to Registration page
    public void onClickNewUserRegister(View view) {
        Intent intent = new Intent(this, NewUserRegisterPage.class);
        startActivity(intent);
    }

    // Do on Back Pressed
    // * Ask the person wants to logout or not. And if yes it logout and exits.
    public void onBackPressed(){
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
        alertDialogBuilder.setTitle("C1ph3R Bank!").setMessage("Do you Want to exit?").setPositiveButton("Back", (dialogInterface, i1) -> {}).setNegativeButton("Exit", (dialogInterface, i1) -> {
            finishAffinity();
        }).show();
    }
}



