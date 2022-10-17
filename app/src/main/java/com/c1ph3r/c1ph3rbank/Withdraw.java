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


public class Withdraw extends AppCompatActivity {
    public UserDataBase userData;
    MaterialButton withdrawButton;
    TextInputLayout amountFieldLayout;
    TextInputEditText amountField, toName;
   Dialog dialog;
    int value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        try{
            customDialogBoxWithdraw();
        } catch(Exception e){
            System.out.println(e.getMessage() + "| OnCreate WithDraw");
        }
    }

    //  Changing the color if the error occurs in the amount field.
    public void error(){
        try{
            amountFieldLayout.setError(getString(R.string.ErrorAmountInvalid));
            amountFieldLayout.setErrorEnabled(true);
            amountFieldLayout.setBoxStrokeErrorColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
            amountFieldLayout.setErrorTextColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
            amountFieldLayout.setHintTextColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
            amountFieldLayout.setErrorIconTintList(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
        }catch(Exception e){
            System.out.println(e.getMessage() + "| error in error");
        }
    }

    // Change the color of the amount field.
    public void onClickAmountField(View view) {
        amountFieldLayout.setErrorEnabled(false);
        amountFieldLayout.setHintTextColor(ColorStateList.valueOf(getColor(R.color.Indigo)));
        amountFieldLayout.setBoxStrokeColor(getColor(R.color.Indigo));

    }
    // Asking the user to confirm the payment or not.
    public void amountConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Withdraw.this);
        builder.setMessage(getString(R.string.DoYouWantToWithraw_String));
        builder.setTitle(getString(R.string.Alert));
        builder.setCancelable(true);
        // If confirm debit the amount from the user.
        builder.setPositiveButton(getString(R.string.Confirm), (DialogInterface.OnClickListener) (dialog, which) -> withdrawAmount());
        // If no cancel the transactions.
        builder.setNegativeButton(getString(R.string.Cancel), (DialogInterface.OnClickListener) (dialog, which) -> {
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // On click confirm pay.
    public void OnClickVerifyPay(View view) {
        try{
            if(userData.getBalance() >= Integer.parseInt(String.valueOf(amountField.getText())) && Integer.parseInt(String.valueOf(amountField.getText()))>= 100){
                amountConfirmation();
                System.out.println(getString(R.string.AmoutDebited_String) + userData.getBalance());
            }else{
                error();
            }
        }catch (Exception e){
            error();
        }
    }

    // when cancel is clicked.
    public void onClickCancelPay(View view) {
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
        finish();
    }

    // to go back the the dashboard when back pressed.
    public void onBackPressed(){
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
        finish();

    }

    // To withdraw amount.
    void withdrawAmount(){
        try{
            // Withdraw amount from the user account and storing the transaction details from the user account.
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
            // Saving the transactions.
            String Transactions = "To: "+ toName.getText() + "    Amount: " + amountField.getText() + "\nOn:" + dateFormat.format(date);
            DebitedValues.put("transactions",Transactions);
            long value = transactions.insert("debit",null,DebitedValues);
            System.out.println("\n\n\n\n\n\n " + value);
            this.dialog.show();
        }catch (Exception e){
            System.out.println(e.getMessage() + "| WithDraw Amount");
        }
    }

    // Display the Confirmation and debit the amount from the user account.
    @SuppressLint("UseCompatLoadingForDrawables")
    private void customDialogBoxWithdraw() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_one);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.gradient1));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        MaterialButton backBtnWithdraw = dialog.findViewById(R.id.backBtnWithdraw);
        withdrawButton = findViewById(R.id.withdrawButton);
        // Getting the user data using the index value.
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.IndexValue_Strings), Context.MODE_PRIVATE);
        value = sharedPreferences.getInt(getString(R.string.value_String),0);
        toName = findViewById(R.id.SenderIDField);
        amountField = findViewById(R.id.AmountField);
        amountFieldLayout = findViewById(R.id.AmountFieldLayout);
        UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(this);
        userDataBaseHelper.getUserDataBase();
        userData = userDataBaseHelper.userDataBase.get(value);
        new TransactionHelper(this, userData.getName()+"Transactions");
        backBtnWithdraw.setOnClickListener(view -> {
            Intent intent = new Intent(Withdraw.this,DashBoard.class);
            startActivity(intent);
            finish();
        });
    }

}