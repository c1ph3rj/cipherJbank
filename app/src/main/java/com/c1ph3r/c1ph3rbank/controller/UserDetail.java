package com.c1ph3r.c1ph3rbank.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
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



    public void getUserDataBase(UserDataBaseHelper userDataBaseHelper){
        this.userDataBaseHelper = userDataBaseHelper;
        userDBRead = userDataBaseHelper.getReadableDatabase();
        contentValues = new ContentValues();
        String[] tableNames = {"accountNumber","userName", "pin", "accountType", "expiryDate", "balance", "loggedIn"};
        cursor = userDBRead.query("userDetails", tableNames,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                int accountNo = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                int pin = Integer.parseInt(cursor.getString(2));
                String accountType = cursor.getString(3);
                String expiryDate = cursor.getString(4);
                int balance = Integer.parseInt(cursor.getString(5));
                boolean loggedIn = Boolean.parseBoolean(cursor.getString(6));
                userDataBase.add(new UserDataBase(name, accountNo, pin, accountType, balance, expiryDate, loggedIn));
            }
        }
        cursor.close();
    }

    public UserDataBase getUserData(int i){
     return userDataBase.get(i);
    }

    public void updateUserData(int i, int balance, UserDataBaseHelper userDataBaseHelper,boolean loggedIn){
        userDBWrite = userDataBaseHelper.getWritableDatabase();
        String[] tableNames = {"accountNumber","userName", "pin", "accountType", "expiryDate", "balance", "loggedIn"};
        Cursor cursor = userDBWrite.query("userDetails", tableNames,null,null,null,null,null);
        getUserDataBase(userDataBaseHelper);
        UserDataBase userData = userDataBase.get(i);
        cursor.moveToFirst();
        String Name = userDataBase.get(i).getName();
        while(cursor.moveToNext()){
            if(Name.equals(cursor.getString(1))){
                contentValues.put("loggedIn",String.valueOf(loggedIn));
                contentValues.put("pin", String.valueOf(userData.getPin()));
                contentValues.put("balance", balance);
                userDBWrite.update("userDetails",contentValues,"userName=?",new String[]{userData.getName()});
                System.out.println("\n\n\n\n\n\n\n" + cursor.getString(5)+"\n\n\n\n\n\n");
            }

        }
        cursor.close();
    }
}