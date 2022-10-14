package com.c1ph3r.c1ph3rbank.controller;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.c1ph3r.c1ph3rbank.model.UserDataBaseHelper;

import java.util.ArrayList;
import java.util.Random;

public class UserRegistration {
    Random random;

    // To set Random Balance from int [] bal array
    String getBalance() {
        int [] bal = {2000, 10000, 5000, 1000,12000, 20000};
        random = new Random();
        String balance =  String.valueOf(bal[random.nextInt(4)]);
        System.out.println(balance);
        return balance;
    }

    // To Set The Random Expiry Date
    String getExpiryDate() {
        int month = random.nextInt(12);
        return month + "/" + ((month<=4)?28:(month<=8)?35:38);
    }

    // To verify the user input. this method returns the int values based on the input.
    // Based on the int value the EditText box in the View will react.
    public int userDataVerification(String userName, String pin, String reEnteredPin, String accountNo, String accountType, ArrayList<UserDataBase> userDataBase, UserDataBaseHelper userDB){
        try {
            // Verifying the data using nested IF else statement.
            if (!userName.isEmpty()) {
                for (int i = 0; i < userDataBase.size(); i++) {
                    // Verify if the userName already exists to the db.
                    if (userName.equals(userDataBase.get(i).getName())) {
                        return 7;
                    }
                }
            } else
                // Both condition is false this will execute.
                return 1;

            // Confirm Pin verification.


            // Pin verification
            if (pin.isEmpty()) {
                return 2;
                // If the pin is less than 4 it won't works
            }
            if (reEnteredPin.isEmpty()) {
                return 3;
            }

            //Account NO verification.
            if (!accountNo.isEmpty()) {
                for (int i = 0; i < userDataBase.size(); i++) {
                    if (accountNo.length() == 8) {
                        if (accountNo.equals(String.valueOf(userDataBase.get(i).getAccountNo()))) {
                            return 8;
                        }
                    } else
                        return 4;
                }
            } else
                return 4;

            // Account Type verification.
            if (accountType.isEmpty()) {

                return 5;
            } else {
                boolean result = addUserToUserDetails(userName, pin, accountNo, accountType, getBalance(), getExpiryDate(), userDB);
                if (result) {
                    return 6;
                } else
                    return 9;
            }
        }
       catch(Exception e){
           System.out.println(e + " | UserVerification");
           return 0;
       }
    }

    // Adding Data to the SQLite DB
    public boolean addUserToUserDetails(String userName, String pin, String accountNo, String accountType, String balance, String expiryDate, UserDataBaseHelper userDB) {
        SQLiteDatabase userDataBase = userDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("accountNumber", accountNo);
        contentValues.put("userName", userName);
        contentValues.put("pin", pin);
        contentValues.put("accountType", accountType);
        contentValues.put("expiryDate", expiryDate);
        contentValues.put("balance", balance);
        contentValues.put("loggedIn", "false");
        long insert = userDataBase.insert("userDetails", null, contentValues);
        // returns false if the data does not add on the sqlite DB.
        return insert != -1;
    }

}
