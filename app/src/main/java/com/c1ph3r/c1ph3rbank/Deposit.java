package com.c1ph3r.c1ph3rbank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.c1ph3r.c1ph3rbank.DBHelper.TransactionHelper;
import com.c1ph3r.c1ph3rbank.Model.UserDataBase;
import com.c1ph3r.c1ph3rbank.DBHelper.UserDataBaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Deposit extends AppCompatActivity {
    public UserDataBase userData;
    MaterialButton depositButton;
    TextInputLayout amountFieldLayout;
    TextInputEditText amountField;
    Dialog dialog;
    UserDataBaseHelper userDataBaseHelper;
    int value;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        try{
            // To deposit the amount to the userDataBase.
            depositButton = findViewById(R.id.DepositButton);
            amountField = findViewById(R.id.AmountFieldDeposit);
            amountFieldLayout = findViewById(R.id.AmountFieldLayoutDeposit);
            CustomDialogDeposit();
        }catch (Exception e){
            System.out.println(e.getMessage() + "| Initialization part in Deposit");
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void CustomDialogDeposit() {
        // to display the custom deposit confirmation.
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.fragment_confirmation_for_deposit);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.gradient1));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("IndexValue", Context.MODE_PRIVATE);
        value = sharedPreferences.getInt("value",0);
        userDataBaseHelper = new UserDataBaseHelper(this);
        userDataBaseHelper.getUserDataBase();
        userData = userDataBaseHelper.userDataBase.get(value);
        MaterialButton backBtnDeposit = dialog.findViewById(R.id.backBtnDeposit);
        backBtnDeposit.setOnClickListener(view -> {
            Intent intent = new Intent(Deposit.this,DashBoard.class);
            startActivity(intent);
            finish();
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
    // To display the error message to the user if the amount is invalid.
    public void error(){
        amountFieldLayout.setError(getString(R.string.ErrorAmountInvalid));
        amountFieldLayout.setErrorEnabled(true);
        amountFieldLayout.setBoxStrokeErrorColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
        amountFieldLayout.setErrorTextColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
        amountFieldLayout.setHintTextColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
        amountFieldLayout.setErrorIconTintList(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
    }

    // to change the color of the amount  field when focused.
    public void onClickAmountFieldDeposit(View view) {
        amountFieldLayout.setErrorEnabled(false);
        amountFieldLayout.setHintTextColor(ColorStateList.valueOf(getColor(R.color.Indigo)));
        amountFieldLayout.setBoxStrokeColor(getColor(R.color.Indigo));

    }

    // Confirming the amount to be deposit.
    public void amountConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Deposit.this);
        builder.setMessage(R.string.Confirm_Deposit_To_the_user);
        builder.setTitle(R.string.Alert);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.Confirm, (DialogInterface.OnClickListener) (dialog, which) -> depositAmount());
        builder.setNegativeButton(R.string.Cancel, (DialogInterface.OnClickListener) (dialog, which) -> {
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // After payment done move to dashboard.
    public void OnClickCancelVerifyDepositPay(View view){
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
        finish();
    }

    // On back pressed Move to the dashboard.
    public void onBackPressed(){
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
        finish();
    }

    // To deposit the amount to the user account and update the data base and store the transactions.
    void depositAmount(){
        try{
            userData.setBalance(userData.getBalance() + Integer.parseInt(String.valueOf(amountField.getText())));
            System.out.println(userData.getBalance());
            userDataBaseHelper = new UserDataBaseHelper(this);
            userDataBaseHelper.updateUserData(value, userData.getBalance(), userData.isLoggedIn() );
            TransactionHelper transactionHelper = new TransactionHelper(this, (userData.getName() + "Transactions"));
            SQLiteDatabase transactions = transactionHelper.getWritableDatabase();
            // To store the transactions.
            @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss");
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            ContentValues creditedValues = new ContentValues();
            String Transactions = "From: C1ph3RBank   Amount: " + amountField.getText() + "\nOn:" + dateFormat.format(date);
            creditedValues.put("transactions",Transactions);
            long value = transactions.insert("credit",null,creditedValues);
            System.out.println("\n\n\n\n\n\n " + value);
            this.dialog.show();
        }catch (Exception e){
            System.out.println(e.getMessage() + "| Deposit Amount");
        }
    }
}