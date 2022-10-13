package com.c1ph3r.c1ph3rbank.controller;

import android.content.ContentValues;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RadioButton;
import android.widget.Toast;

import com.c1ph3r.c1ph3rbank.MainActivity;
import com.c1ph3r.c1ph3rbank.NewUserRegisterPage;
import com.c1ph3r.c1ph3rbank.R;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.c1ph3r.c1ph3rbank.model.UserDataBaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Random;

public class UserRegistration {
    Random random;
    private NewUserRegisterPage newUser = null;

    int getBalance() {
        int [] bal = {12000, 10000, 5000, 1000, 20000};
        random = new Random();
        return  bal[random.nextInt(bal.length - 1)];
    }

    String getExpiryDate() {
        int month = random.nextInt(12);
        return month + "/" + ((month<=4)?28:(month<=8)?35:38);
    }

    public String userName, pin, reEnteredPin, accountNo, accountType, expiryDate, balance;
    TextInputLayout userNameLayout, pinLayout, reEnteredPinLayout, accountNoLayout;
    UserDataBaseHelper userDataBaseHelper;



    public UserRegistration() {

    }

    public void userRegistrationInit(NewUserRegisterPage newUserRegisterPage){
        try {
            userDataBaseHelper = new UserDataBaseHelper(newUser);
            this.newUser = newUserRegisterPage;
            userNameLayout = newUser.findViewById(R.id.UserNameLayoutR);
            pinLayout = newUser.findViewById(R.id.PinLayoutR);
            reEnteredPinLayout = newUser.findViewById(R.id.ReEnterPinLayout);
            accountNoLayout = newUser.findViewById(R.id.accountNumberLayoutR);
            userName = String.valueOf(newUser.newUserName.getText());
            accountNo = String.valueOf(newUser.newAccountNumber.getText());
            pin = String.valueOf(newUser.newPin.getText());
            reEnteredPin = String.valueOf(newUser.reEnterPin.getText());
            accountType = newUser.accountType;
        }catch(Exception e){
            System.out.println("\n\n\n\n\n " + e.getMessage()  +" || Error In Init " + "\n\n\n\n\n");
        }
    }



    public void userDataVerification(){
//       try{
           if(!userName.isEmpty()){
               if(!pin.isEmpty()){
                   if(!reEnteredPin.isEmpty()){
                       if(!accountNo.isEmpty()){
                           if(!accountType.isEmpty()){
                               balance = String.valueOf(getBalance());
                               expiryDate = getExpiryDate();
                               System.out.println("here1");
                               boolean result = userDataBaseHelper.addUserToUserDetails(UserRegistration.this, newUser);
                               System.out.println("here2");
                               if(!result)
                                   Toast.makeText(newUser, R.string.IfRegistrationFailed, Toast.LENGTH_SHORT).show();
                               else
                                   Toast.makeText(newUser, newUser.getString(R.string.IfRegistrationSuccess), Toast.LENGTH_SHORT).show();
                           }else{
                               Toast.makeText(newUser,newUser.getString(R.string.IfAccountTypeIsEmpty),Toast.LENGTH_SHORT ).show();
                           }
                       }else
                           errorDisplay(accountNoLayout, newUser.getString(R.string.IfAccountNoIsEmpty));
                   }else
                       errorDisplay(reEnteredPinLayout, newUser.getString(R.string.IfReEnterPinIsEmpty));
               }else
                   errorDisplay(pinLayout, newUser.getString(R.string.IfPinIsEmpty));
           }else
               errorDisplay(userNameLayout, newUser.getString(R.string.IfUserNameIsEmpty));
//       }catch(Exception e){
//           System.out.println(e);
//       }

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
