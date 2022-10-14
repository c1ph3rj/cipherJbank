package com.c1ph3r.c1ph3rbank.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.c1ph3r.c1ph3rbank.model.UserDataBaseHelper;

import java.util.ArrayList;


public class UserDetail {


    // THIS IS THE WHERE THE DATA IS STORED. AND THIS IS THE PART OF THE MAIN-ACTIVITY.class
    public ArrayList<UserDataBase> userDataBase = new ArrayList<>();
    ContentValues contentValues;
    SQLiteDatabase userDBWrite;
    SQLiteDatabase userDBRead;
    Cursor cursor;
    UserDataBaseHelper userDataBaseHelper;


    // Getting the userDataBase SQLite DB and saving it into an Model class.
    public void getUserDataBase(UserDataBaseHelper userDataBaseHelper){
        this.userDataBaseHelper = userDataBaseHelper;
        userDBRead = userDataBaseHelper.getReadableDatabase();
        contentValues = new ContentValues();
        // Tables inside the DB .
        String[] tableNames = {"accountNumber","userName", "pin", "accountType", "expiryDate", "balance", "loggedIn"};
        cursor = userDBRead.query("userDetails", tableNames,null,null,null,null,null);
        // Getting the values from the DB and assign it to the Model Class.
        if(cursor.moveToFirst()) {
            do {
                int accountNo = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                int pin = Integer.parseInt(cursor.getString(2));
                String accountType = cursor.getString(3);
                String expiryDate = cursor.getString(4);
                int balance = Integer.parseInt(cursor.getString(5));
                boolean loggedIn = Boolean.parseBoolean(cursor.getString(6));
                // Adding the Data to the userDataBase Arraylist. (Model Class)
                userDataBase.add(new UserDataBase(name, accountNo, pin, accountType, balance, expiryDate, loggedIn));
            }while (cursor.moveToNext());
        }
        cursor.close();
    }


    // Returns the UserData of i th index in the userDataBase ArrayList.
    public UserDataBase getUserData(int i){
     return userDataBase.get(i);
    }

    // Method to update the values inside the DB.
    public void updateUserData(int i, int balance, UserDataBaseHelper userDataBaseHelper,boolean loggedIn){
        userDBWrite = userDataBaseHelper.getWritableDatabase();
        String[] tableNames = {"accountNumber","userName", "pin", "accountType", "expiryDate", "balance", "loggedIn"};
        Cursor cursor = userDBWrite.query("userDetails", tableNames,null,null,null,null,null);
        getUserDataBase(userDataBaseHelper);
        UserDataBase userData = userDataBase.get(i);
        cursor.moveToFirst();
        String Name = userDataBase.get(i).getName();
        do{
            if(Name.equals(cursor.getString(1))){
                // Replacing the values according to the user Request.
                contentValues.put("loggedIn",String.valueOf(loggedIn));
                contentValues.put("pin", String.valueOf(userData.getPin()));
                contentValues.put("balance", balance);
                userDBWrite.update("userDetails",contentValues,"userName=?",new String[]{userData.getName()});
                System.out.println("\n\n\n\n\n\n\n" + cursor.getString(5)+"\n\n\n\n\n\n");
            }
        // Moving the cursor until its done.
        }while(cursor.moveToNext());
        cursor.close();
    }
}