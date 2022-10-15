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

import com.c1ph3r.c1ph3rbank.model.TransactionHelper;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.c1ph3r.c1ph3rbank.model.UserDataBaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Method to Withdraw money from the UserAccounts.
public class Withdraw extends AppCompatActivity {
    // Declaring the reusable variables.
    public UserDataBase userData;
    UserDataBaseHelper userDataBaseHelper;
    MaterialButton withdrawButton;
    TextInputLayout amountFieldLayout;
    TextInputEditText amountField, toName;
    Dialog dialog;
    int value;

    // Method executes when the Activity get started.
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        try{
           customDialogBox();
        } catch(Exception e){
            System.out.println(e.getMessage() + "| OnCreate WithDraw");
        }
    }

    private void customDialogBox() {
        // Custom Dialog box with Confirmation message for the transaction.
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
        userDataBaseHelper = new UserDataBaseHelper(this);
        userDataBaseHelper.getUserDataBase();
        userData = userDataBaseHelper.userDataBase.get(value);
        new TransactionHelper(this, userData.getName()+"Transactions");
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
        try{
            amountFieldLayout.setError("Invalid Amount");
            amountFieldLayout.setErrorEnabled(true);
            amountFieldLayout.setBoxStrokeErrorColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
            amountFieldLayout.setErrorTextColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
            amountFieldLayout.setHintTextColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
            amountFieldLayout.setErrorIconTintList(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
        }catch(Exception e){
            System.out.println(e.getMessage() + "| error in error");
        }
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
        try{
            userData.setBalance(userData.getBalance() - Integer.parseInt(String.valueOf(amountField.getText())));
            System.out.println(userData.getBalance());
            UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(this);
            userDataBaseHelper.updateUserData(value, userData.getBalance(), userData.isLoggedIn() );
            TransactionHelper transactionHelper = new TransactionHelper(this, (userData.getName() + "Transactions"));
            SQLiteDatabase transactions = transactionHelper.getWritableDatabase();
            @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss");
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            ContentValues DebitedValues = new ContentValues();
            String Transactions = "To: "+ toName.getText() + "    Amount: " + amountField.getText() + "\nOn:" + dateFormat.format(date);
            DebitedValues.put("transactions",Transactions);
            long value = transactions.insert("debit",null,DebitedValues);
            System.out.println("\n\n\n\n\n\n " + value);
            this.dialog.show();
        }catch (Exception e){
            System.out.println(e.getMessage() + "| WithDraw Amount");
        }
    }

}