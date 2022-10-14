package com.c1ph3r.c1ph3rbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.c1ph3r.c1ph3rbank.controller.UserDetail;
import com.c1ph3r.c1ph3rbank.controller.UserVerification;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.c1ph3r.c1ph3rbank.model.UserDataBaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


/*TODO :
    -- Primary --
    Verify if the username is available or not --done
    Verify userName and password using the SQLite queries -- half done
    Add transactions page
    Handle errors using try catch
    Convert strings to string resource file
    Clean up code finally
    Add comments
    -- Secondary --
    Add is user logged in feature --done
    Add change pin option
    Clean the ui
    Add logout feature
    Add splash screen effect if possible and utilize the time to verify is user Logged in
*/


public class MainActivity extends AppCompatActivity {
    public UserDetail userDetail;
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

        userDetail = new UserDetail();
        UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(this);
        userDetail.getUserDataBase(userDataBaseHelper);
        userVerification = new UserVerification();
        userVerification.dataRequiredForVerification(MainActivity.this,userName,pin,pinLayout,userNameLayout);
        userVerification.changeColorOfInputs();

        for(int i =0;i<userDetail.userDataBase.size();i++){
            if(userDetail.userDataBase.get(i).isLoggedIn()){
                Intent intent = new Intent(this, DashBoard.class);
                SharedPreferences sharedPreferences = this.getSharedPreferences("IndexValue", Context.MODE_PRIVATE);
                SharedPreferences.Editor editValues = sharedPreferences.edit();
                editValues.putInt("value",userDetail.userDataBase.indexOf(userDetail.userDataBase.get(i)));
                editValues.apply();
                startActivity(intent);
                finish();
            }
        }

    }

    public void onClickLoginBtn(View view) {
        userDetail = new UserDetail();
        UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(this);
        userDetail.getUserDataBase(userDataBaseHelper);
        ArrayList<UserDataBase> userDataBase = userDetail.userDataBase;
        userVerification.verifyTheUser(userDataBase);
        // Query part
        SQLiteDatabase userDB = userDataBaseHelper.getReadableDatabase();
        userVerification.verifyTheUser(userDB, userName, pin);
        //Query part


    }

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



