package com.c1ph3r.c1ph3rbank.controller;

import com.c1ph3r.c1ph3rbank.model.UserDataBase;

import java.util.ArrayList;

public class UserRegistration {

    // To verify the user input. this method returns the int values based on the input.
    // Based on the int value the EditText box in the View will react.
    public int userDataVerification(String userName, String pin, String reEnteredPin, String accountNo, String accountType, ArrayList<UserDataBase> userDataBase){
        try{
           // Verifying the data using nested IF else statement.
           if(!userName.isEmpty()){
               for(int i = 0;i<userDataBase.size();i++){
                   if(userName.equals(userDataBase.get(i).getName())){
                       return 7;
                   }
               }
           }else
                return 1;
            if(pin.isEmpty()){
                return 2;
            }
            if(reEnteredPin.isEmpty()){
                return 3;
            }
            if(!accountNo.isEmpty()){
                for(int i = 0;i<userDataBase.size();i++){
                    if(accountNo.equals (String.valueOf(userDataBase.get(i).getAccountNo()))){
                        return 8;
                    }
                }
            }else
                return 4;
            if(accountType.isEmpty()){

                return 5;
            }else{
                return 6;
            }
       }

       catch(Exception e){
           System.out.println(e);
           return 0;
       }
    }

}
