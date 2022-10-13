package com.c1ph3r.c1ph3rbank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.c1ph3r.c1ph3rbank.controller.UserDetail;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.c1ph3r.c1ph3rbank.model.UserDataBaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Deposit extends AppCompatActivity {
    public UserDataBase userData;
    UserDetail userDetail;
    MaterialButton depositButton;
    TextInputLayout amountFieldLayout;
    TextInputEditText amountField;
    Dialog dialog;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        Intent intent = getIntent();
        depositButton = findViewById(R.id.DepositButton);
        amountField = findViewById(R.id.AmountFieldDeposit);
        amountFieldLayout = findViewById(R.id.AmountFieldLayoutDeposit);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.fragment_confirmation_for_deposit);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.gradient1));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("IndexValue", Context.MODE_PRIVATE);
        value = sharedPreferences.getInt("value",0);
        userDetail = new UserDetail();
        UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(this);
        userDetail.getUserDataBase(userDataBaseHelper);
        userData = userDetail.userDataBase.get(value);
        MaterialButton backBtnDeposit = dialog.findViewById(R.id.backBtnDeposit);
        backBtnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Deposit.this,DashBoard.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void OnClickVerifyDepositPay(View view) {
        try{
            if(Integer.parseInt(String.valueOf(amountField.getText()))>= 100){
                amountConfirmation();
            }else{
                error();
            }
        }catch (Exception e){
            error();
        }
    }

    public void error(){
        amountFieldLayout.setError(getString(R.string.ErrorAmountInvalid));
        amountFieldLayout.setErrorEnabled(true);
        amountFieldLayout.setBoxStrokeErrorColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
        amountFieldLayout.setErrorTextColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
        amountFieldLayout.setHintTextColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
        amountFieldLayout.setErrorIconTintList(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
    }


    public void onClickAmountFieldDeposit(View view) {
        amountFieldLayout.setErrorEnabled(false);
        amountFieldLayout.setHintTextColor(ColorStateList.valueOf(getColor(R.color.Indigo)));
        amountFieldLayout.setBoxStrokeColor(getColor(R.color.Indigo));

    }

    public void amountConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Deposit.this);
        builder.setMessage(R.string.Confirm_Deposit_To_the_user);
        builder.setTitle(R.string.Alert);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.Confirm, (DialogInterface.OnClickListener) (dialog, which) -> {
            userData.setBalance(userData.getBalance() + Integer.parseInt(String.valueOf(amountField.getText())));
            System.out.println(userData.getBalance());
            UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(this);
            userDetail.updateUserData(value, userData.getBalance(),userDataBaseHelper, userData.isLoggedIn() );
            this.dialog.show();
        });
        builder.setNegativeButton(R.string.Cancel, (DialogInterface.OnClickListener) (dialog, which) -> {
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void OnClickCancelVerifyDepositPay(View view){
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
        finish();
    }
}