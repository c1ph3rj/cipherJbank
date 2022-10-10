package com.c1ph3r.c1ph3rbank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;

import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Deposit extends AppCompatActivity {
    public UserDataBase userData;
    MaterialButton depositButton;
    TextInputLayout amountFieldLayout;
    TextInputEditText amountField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        Intent intent = getIntent();
        depositButton = findViewById(R.id.DepositButton);
        userData = (UserDataBase) intent.getSerializableExtra("value");
        amountField = findViewById(R.id.AmountFieldDeposit);
        amountFieldLayout = findViewById(R.id.AmountFieldLayoutDeposit);
    }

    public void OnClickVerifyDepositPay(View view) {
        try{
            if(Integer.parseInt(String.valueOf(amountField.getText()))>= 100){
                amountConfirmation();
                System.out.println("Amount Has been taken from your account.\nBalance:" + userData.getBalance());
            }else{
                error();
            }
        }catch (Exception e){
            error();
        }
    }

    public void error(){
        amountFieldLayout.setError("Invalid Amount");
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

        builder.setMessage("Do you want to Deposit the Amount ?");
        builder.setTitle("Alert !");
        builder.setCancelable(true);
        builder.setPositiveButton("Confirm", (DialogInterface.OnClickListener) (dialog, which) -> {
            userData.setBalance(userData.getBalance() + Integer.parseInt(String.valueOf(amountField.getText())));
            System.out.println(userData.getBalance());
            getSupportFragmentManager().beginTransaction().replace(R.id.DepositLayout, new ConfirmationForDeposit(userData)).commit();
        });
        builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}