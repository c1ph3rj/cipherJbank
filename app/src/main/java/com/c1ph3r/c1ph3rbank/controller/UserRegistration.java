package com.c1ph3r.c1ph3rbank.controller;

import com.c1ph3r.c1ph3rbank.model.UserDataBase;

import java.util.ArrayList;
import java.util.Random;

public class UserRegistration {
    Random random;

    // To set Random Balance from int [] bal array
    public String getBalance() {
        int [] bal = {2000, 10000, 5000, 1000,12000, 20000};
        random = new Random();
        String balance =  String.valueOf(bal[random.nextInt(4)]);
        System.out.println(balance);
        return balance;
    }

    // To Set The Random Expiry Date
    public String getExpiryDate() {
        int month = random.nextInt(12);
        return month + "/" + ((month<=4)?28:(month<=8)?35:38);
    }

    // To verify the user input. this method returns the int values based on the input.
    // Based on the int value the EditText box in the View will react.
    public int userDataVerification(String userName, String pin, String reEnteredPin, String accountNo, String accountType, ArrayList<UserDataBase> userDataBase){
        try {
            // User Name Verification.
            if(userName.matches("^[a-zA-Z][0-9a-zA-Z_]{4,26}$")){
                for (int i = 0; i < userDataBase.size(); i++) {
                    // Verify if the userName already exists to the db.
                    if (userName.equals(userDataBase.get(i).getName())) {
                        return 7;
                    }
                }
            }else
                return 1;

            // Pin verification.
            if(pin.length()==4){
                if(!pin.equals(reEnteredPin)){
                    return 3;
                }
            }else
                return 2;

            // Account Type Verification.
            if (accountType.isEmpty()) {

                return 5;
            }
            // Account NO verification.
            if (accountNo.matches("^[0-9]{8}$")) {
                for (int i = 0; i < userDataBase.size(); i++) {
                    if (accountNo.equals(String.valueOf(userDataBase.get(i).getAccountNo())))
                        return 8;
                }
                return 6;

            } else
                return 4;
        }
       catch(Exception e){
           System.out.println(e + " | UserVerification");
           return 0;
       }
    }
}
