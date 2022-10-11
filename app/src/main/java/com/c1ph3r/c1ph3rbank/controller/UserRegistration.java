package com.c1ph3r.c1ph3rbank.controller;

import android.content.ContentValues;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RadioButton;
import android.widget.Toast;

import com.c1ph3r.c1ph3rbank.NewUserRegisterPage;
import com.c1ph3r.c1ph3rbank.R;
import com.c1ph3r.c1ph3rbank.model.UserDataBaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Random;

public class UserRegistration {

    private ArrayList<String> users;
    private NewUserRegisterPage newUser = null;
    String userName, pin, reEnteredPin, accountNo, accountType, expiryDate;
    TextInputLayout userNameLayout, pinLayout, reEnteredPinLayout, accountNoLayout;
    ContentValues contentValues;
    SQLiteDatabase userDBWrite;
    SQLiteDatabase userDBRead;
    Cursor cursor;
    UserDataBaseHelper userDataBaseHelper;

    public UserRegistration(NewUserRegisterPage newUserRegisterPage) {
        try {
            this.newUser = newUserRegisterPage;
            userNameLayout = newUser.findViewById(R.id.UserNameLayoutR);
            pinLayout = newUser.findViewById(R.id.PinLayoutR);
            reEnteredPinLayout = newUser.findViewById(R.id.ReEnterPinLayout);
            accountNoLayout = newUser.findViewById(R.id.accountNumberLayoutR);
            userName = String.valueOf(newUser.newUserName.getText());
            accountNo = String.valueOf(newUser.newAccountNumber.getText());
            pin = String.valueOf(newUser.newPin.getText());
            reEnteredPin = String.valueOf(newUser.reEnterPin.getText());
            accountType = String.valueOf(((RadioButton)newUser.findViewById(newUser.newAccountType.getCheckedRadioButtonId())).getText());
        }catch(Exception e){
            System.out.println("\n\n\n\n\n " + e.getMessage()  + "\n\n\n\n\n");
        }

        userDataBaseHelper = new UserDataBaseHelper(newUser);
        userDBWrite = userDataBaseHelper.getWritableDatabase();
        userDBRead = userDataBaseHelper.getReadableDatabase();
        contentValues = new ContentValues();
        String[] tableNames = {"accountNumber","userName", "pin", "accountType", "expiryDate"};
        cursor = userDBRead.query("userDetails", tableNames,null,null,null,null,null);

    }

    public UserRegistration(){

    }


    public void userDataVerification(){
        boolean isUserNameOK = false, isPinOk = false, isAccountNoOk = false, isAccountTypeOK = false;
        if(!userName.isEmpty()){
            while(cursor.moveToNext()){
                if(userName.equals(cursor.getString(1))){
                    String error = "UserName Already Exists!";
                    errorDisplay(userNameLayout, error);
                    break;
                }else{
                    contentValues.put("userName", userName);
                    isUserNameOK = true;
                }
            }
        }else{
            String error ="Enter a UserName.";
            errorDisplay(userNameLayout, error);
        }
        if(!pin.isEmpty()&&!reEnteredPin.isEmpty()&&pin.equals(reEnteredPin)){
            contentValues.put("pin", pin);
            isPinOk = true;
        }else{
            String error = pin.isEmpty() ?"Enter a Pin.": reEnteredPin.isEmpty() ?"Enter Confirm Pin.": "Pin Does not match.";
            if(reEnteredPin.isEmpty()&&!pin.isEmpty())
                errorDisplay(reEnteredPinLayout, error);
            else errorDisplay(pinLayout, error);
        }
        if(accountNo.length() == 8){
            while(cursor.moveToPrevious()){
                if(accountNo.equals(cursor.getString(0))){
                    String error = "Account NO already exists";
                    errorDisplay(accountNoLayout, error);
                    break;
                }else{
                    contentValues.put("accountNumber", accountNo);
                    isAccountNoOk = true;
                }
            }
        }else{
            String error ="Account Number invalid.";
            errorDisplay(accountNoLayout, error);
        }
        try{
            if(!accountType.isEmpty()){
                contentValues.put("accountType", accountType);
                isAccountTypeOK = true;
            }
        }catch(Exception e){
            Toast.makeText(newUser, "Choose Account Type.", Toast.LENGTH_SHORT).show();
        }
        System.out.println("\n\n\n\n\n\n" + userName + accountNo + accountType + pin + accountNo.length() + "\n "+isAccountNoOk + isPinOk + isAccountTypeOK + isUserNameOK );
        if(isUserNameOK&&isPinOk&&isAccountNoOk&&isAccountTypeOK){
            Random random = new Random();
            int month = random.nextInt(12);
            expiryDate = month + "/" + ((month<=4)?28:(month<=8)?35:38);
            contentValues.put("expiryDate", expiryDate);
            userDBWrite = userDataBaseHelper.getWritableDatabase();
            long inserted = userDBWrite.insert("userDetails",null, contentValues);
            if(inserted>0){
                Toast.makeText(newUser, "Account Created.", Toast.LENGTH_SHORT).show();
                        newUser.finish();
            }else
                Toast.makeText(newUser, "Failed TryAgain!", Toast.LENGTH_SHORT).show();
        }
    }


    public void errorDisplay(TextInputLayout Layout, String error){
        Layout.setError(error);
        Layout.setErrorEnabled(true);
        Layout.setBoxStrokeErrorColor(ColorStateList.valueOf(newUser.getColor(R.color.ErrorRed)));
        Layout.setErrorTextColor(ColorStateList.valueOf(newUser.getColor(R.color.ErrorRed)));
        Layout.setHintTextColor(ColorStateList.valueOf(newUser.getColor(R.color.ErrorRed)));
        Layout.setErrorIconTintList(ColorStateList.valueOf(newUser.getColor(R.color.ErrorRed)));
    }

    public void changeColorOfInputsRegistration(TextInputLayout Layout, TextInputEditText editText) {
        editText.setOnFocusChangeListener((view, b) -> {
            if(Layout.isErrorEnabled()){
                try{
                    Layout.setErrorEnabled(false);
                    Layout.setHintTextColor(Layout.getDefaultHintTextColor());
                    Layout.setBoxStrokeColor(newUser.getColor(R.color.Indigo));
                }catch(Exception e){
                    System.out.println("\n\n\n\n\n " + e.getMessage()  + "\n\n\n\n\n");
                }

            }
        });
    }
}
