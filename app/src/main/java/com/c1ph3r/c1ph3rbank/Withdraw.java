package com.c1ph3r.c1ph3rbank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.c1ph3r.c1ph3rbank.controller.UserDetail;
import com.c1ph3r.c1ph3rbank.model.TransactionHelper;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.c1ph3r.c1ph3rbank.model.UserDataBaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Withdraw extends AppCompatActivity {
    public UserDataBase userData;
    MaterialButton withdrawButton;
    TextInputLayout amountFieldLayout;
    TextInputEditText amountField, toName;
    UserDetail userDetail;
    Dialog dialog;
    int value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_one);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.gradient1));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        MaterialButton backBtnWithdraw = dialog.findViewById(R.id.backBtnWithdraw);
        withdrawButton = findViewById(R.id.withdrawButton);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("IndexValue", Context.MODE_PRIVATE);
        value = sharedPreferences.getInt("value",0);
        toName = findViewById(R.id.SenderIDField);
        amountField = findViewById(R.id.AmountField);
        amountFieldLayout = findViewById(R.id.AmountFieldLayout);
        userDetail = new UserDetail();
        UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(this);
        userDetail.getUserDataBase(userDataBaseHelper);
        userData = userDetail.userDataBase.get(value);
        TransactionHelper transactionHelper = new TransactionHelper(this, userData.getName()+"Transactions");
        backBtnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Withdraw.this,DashBoard.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void error(){
        amountFieldLayout.setError("Invalid Amount");
        amountFieldLayout.setErrorEnabled(true);
        amountFieldLayout.setBoxStrokeErrorColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
        amountFieldLayout.setErrorTextColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
        amountFieldLayout.setHintTextColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
        amountFieldLayout.setErrorIconTintList(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
    }


    public void onClickAmountField(View view) {
        amountFieldLayout.setErrorEnabled(false);
        amountFieldLayout.setHintTextColor(ColorStateList.valueOf(getColor(R.color.Indigo)));
        amountFieldLayout.setBoxStrokeColor(getColor(R.color.Indigo));

    }

    public void amountConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Withdraw.this);

        builder.setMessage("Do you want to Withdraw ?");
        builder.setTitle("Alert !");
        builder.setCancelable(true);
        builder.setPositiveButton("Confirm", (DialogInterface.OnClickListener) (dialog, which) -> {
                withdrawAmount();
        });
        builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void OnClickVerifyPay(View view) {
        try{
            if(userData.getBalance() >= Integer.parseInt(String.valueOf(amountField.getText())) && Integer.parseInt(String.valueOf(amountField.getText()))>= 100){
                amountConfirmation();
                System.out.println("Amount Has been taken from your account.\nBalance:" + userData.getBalance());
            }else{
                error();
            }
        }catch (Exception e){
            error();
        }
    }

    public void onClickCancelPay(View view) {
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
        finish();

    }

    void withdrawAmount(){
        userData.setBalance(userData.getBalance() - Integer.parseInt(String.valueOf(amountField.getText())));
        System.out.println(userData.getBalance());
        UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(this);
        userDetail.updateUserData(value, userData.getBalance(),userDataBaseHelper, userData.isLoggedIn() );
        TransactionHelper transactionHelper = new TransactionHelper(this, (userData.getName() + "Transactions"));
        SQLiteDatabase transactions = transactionHelper.getWritableDatabase();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        ContentValues DebitedValues = new ContentValues();
        String Transactions = "To: "+ toName.getText() + "    Amount: " + amountField.getText() + "\nOn:" + dateFormat.format(date);
        DebitedValues.put("transactions",Transactions);
        long value = transactions.insert("debit",null,DebitedValues);
        System.out.println("\n\n\n\n\n\n " + value);
        this.dialog.show();
    }

}