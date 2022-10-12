package com.c1ph3r.c1ph3rbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;

import com.c1ph3r.c1ph3rbank.controller.UserDetail;
import com.c1ph3r.c1ph3rbank.controller.UserVerification;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


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

        userDetail = new UserDetail(MainActivity.this);
        userDetail.getUserDataBase();
        userVerification = new UserVerification(MainActivity.this,userName,pin,pinLayout,userNameLayout);
        userVerification.changeColorOfInputs();


    }

    public void onClickLoginBtn(View view) {
       userVerification.verifyTheUser();

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

    public void onClickNewUserRegister(View view) {
        Intent intent = new Intent(this, NewUserRegisterPage.class);
        startActivity(intent);
    }
    public void onBackPressed(){
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
        alertDialogBuilder.setTitle("UserDetails").setMessage("Do you Want to exit?").setPositiveButton("Back", (dialogInterface, i1) -> {}).setNegativeButton("Exit", (dialogInterface, i1) -> {
            finishAffinity();
        }).show();
    }
}