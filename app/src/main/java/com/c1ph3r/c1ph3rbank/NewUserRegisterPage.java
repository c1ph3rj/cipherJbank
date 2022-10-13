package com.c1ph3r.c1ph3rbank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.c1ph3r.c1ph3rbank.controller.UserDetail;
import com.c1ph3r.c1ph3rbank.controller.UserRegistration;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import java.io.IOException;

public class NewUserRegisterPage extends AppCompatActivity {
    public TextInputEditText newUserName, newPin, newAccountNumber, reEnterPin;
    public TextInputLayout userNameLayout, pinLayout, reEnteredPinLayout, accountNoLayout;
    UserRegistration userRegistration;
    public RadioGroup newAccountType;
    public String accountType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_register_page);
        newUserName = findViewById(R.id.UserNameFieldR);
        newPin = findViewById(R.id.PinFieldR);
        newAccountNumber = findViewById(R.id.accountNumberR);
        reEnterPin = findViewById(R.id.ReEnterPinField);
        newAccountType = findViewById(R.id.accountTypeR);
        newAccountType.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton button = findViewById(newAccountType.getCheckedRadioButtonId());
            accountType = String.valueOf(button.getText());
            System.out.println(accountType);
        });
        userNameLayout = findViewById(R.id.UserNameLayoutR);
        pinLayout = findViewById(R.id.PinLayoutR);
        reEnteredPinLayout = findViewById(R.id.ReEnterPinLayout);
        accountNoLayout = findViewById(R.id.accountNumberLayoutR);
        userRegistration = new UserRegistration();
        userRegistration.userRegistrationInit(NewUserRegisterPage.this);
        userRegistration.changeColorOfInputsRegistration(userNameLayout, newUserName);
        userRegistration.changeColorOfInputsRegistration(pinLayout, newPin);
        userRegistration.changeColorOfInputsRegistration(reEnteredPinLayout, reEnterPin);
        userRegistration.changeColorOfInputsRegistration(accountNoLayout, newAccountNumber);
    }

    public void onClickSubmitBtn(View view) {
            UserRegistration userRegistration = new UserRegistration();
            userRegistration.userRegistrationInit(NewUserRegisterPage.this);
            userRegistration.userDataVerification();
    }



}