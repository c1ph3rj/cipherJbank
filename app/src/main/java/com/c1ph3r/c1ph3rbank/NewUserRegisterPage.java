package com.c1ph3r.c1ph3rbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.c1ph3r.c1ph3rbank.controller.UserRegistration;
import com.c1ph3r.c1ph3rbank.DBHelper.TransactionHelper;
import com.c1ph3r.c1ph3rbank.DBHelper.UserDataBaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class NewUserRegisterPage extends AppCompatActivity {
    public TextInputEditText newUserName, newPin, newAccountNumber, reEnterPin;
    public TextInputLayout userNameLayout, pinLayout, reEnteredPinLayout, accountNoLayout;
    public String userName, pin, reEnteredPin, accountNo;
    UserRegistration userRegistration;
    public RadioGroup newAccountType;
    public String accountType = "";
    UserDataBaseHelper userDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_register_page);

        // Initializing The Activity Components
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

        //Creating Object of Controller class and SQLite Helper Class
        userDataBaseHelper = new UserDataBaseHelper(this);
        userRegistration = new UserRegistration();

        // Calling a Method to Change the colors.
        changeColorOfInputsRegistration(userNameLayout, newUserName);
        changeColorOfInputsRegistration(pinLayout, newPin);
        changeColorOfInputsRegistration(reEnteredPinLayout, reEnterPin);
        changeColorOfInputsRegistration(accountNoLayout, newAccountNumber);

    }


    // While clicking the Submit button this method will execute.
    public void onClickSubmitBtn(View view) {

        userDataBaseHelper.getUserDataBase();

        // Converting the editable text values to String values.
        userName = String.valueOf(newUserName.getText());
        accountNo = String.valueOf(newAccountNumber.getText());
        pin = String.valueOf(newPin.getText());
        reEnteredPin = String.valueOf(reEnterPin.getText());


        // Creating the Object for the userRegistration.
            UserRegistration userRegistration = new UserRegistration();
            // Initializing the value inside the user registration.
            // Based on the result it calls the method inside the UserRegistration.
            int resultCode = userRegistration.userDataVerification(userName, pin,reEnteredPin, accountNo, accountType, userDataBaseHelper.userDataBase);
            switch (resultCode){
                case 1:
                    // if the result code is 1 then userName layout color will change otherwise it is ignored.
                    errorDisplay(userNameLayout, getString(R.string.IfUserNameIsEmpty));
                    break;
                case 2:
                    errorDisplay(pinLayout, getString(R.string.IfPinIsEmpty));
                    break;
                case 3:
                    errorDisplay(reEnteredPinLayout, getString(R.string.IfReEnterPinIsEmpty));
                    break;
                case 4:
                    errorDisplay(accountNoLayout, getString(R.string.IfAccountNoIsEmpty));
                    break;
                case 5:
                    Toast.makeText(this, getString(R.string.IfAccountTypeIsEmpty),Toast.LENGTH_SHORT ).show();
                    break;
                case 6:
                    boolean result = userDataBaseHelper.addUserToUserDetails(userName, pin, accountNo, accountType, userRegistration.getBalance(),  userRegistration.getExpiryDate());
                    if(result){
                        Toast.makeText(this, getString(R.string.IfRegistrationSuccess), Toast.LENGTH_SHORT).show();
                        new TransactionHelper(this, (userName + "Transactions"));
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }else
                        Toast.makeText(this, R.string.IfRegistrationFailed, Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    errorDisplay(userNameLayout,  getString(R.string.userNameAlreadyExists));
                    break;
                case 8:
                    errorDisplay(accountNoLayout, getString(R.string.AccountNumberAlreadyExists));
                    break;
            }
    }

    // Here is the Code for displaying errors
    // which gets the TextInput Layout and an error text as an input and change the colors accordingly
    // For this I needed the activity to get the Resource Directory.
    public void errorDisplay(TextInputLayout Layout, String error){
        Layout.setError(error);
        Layout.setErrorEnabled(true);
        //Using the activity object to access the resource directory
        Layout.setBoxStrokeErrorColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
        Layout.setErrorTextColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
        Layout.setHintTextColor(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
        Layout.setErrorIconTintList(ColorStateList.valueOf(getColor(R.color.ErrorRed)));
    }

    // Changing the colors of the input when the user focus the field.
    public void changeColorOfInputsRegistration(TextInputLayout Layout, TextInputEditText editText) {
        editText.setOnFocusChangeListener((view, b) -> {
            if(Layout.isErrorEnabled()){
                try{
                    Layout.setErrorEnabled(false);
                    Layout.setHintTextColor(Layout.getDefaultHintTextColor());
                    Layout.setBoxStrokeColor(getColor(R.color.Indigo));
                }catch(Exception e){
                    System.out.println("\n\n\n\n\n " + e.getMessage()  + "\n\n\n\n\n");
                }

            }
        });
    }

}