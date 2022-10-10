package com.c1ph3r.c1ph3rbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;

import com.c1ph3r.c1ph3rbank.controller.UserDetail;
import com.c1ph3r.c1ph3rbank.controller.UserVerification;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import java.io.IOException;


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
        userVerification = new UserVerification(MainActivity.this,userName,pin,pinLayout,userNameLayout);
        try {
            userDetail.getData();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
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
}