package com.c1ph3r.c1ph3rbank.controller;

import android.app.Activity;
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


    public UserDetail(Activity activity) {
        userDataBaseHelper = new UserDataBaseHelper(activity);
        userDBRead = userDataBaseHelper.getReadableDatabase();
        contentValues = new ContentValues();
        String[] tableNames = {"accountNumber","userName", "pin", "accountType", "expiryDate", "balance"};
        cursor = userDBRead.query("userDetails", tableNames,null,null,null,null,null);
        cursor.moveToFirst();
        getUserDataBase();
    }

    public void getUserDataBase(){
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            int accountNo =  Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1);
            int pin = Integer.parseInt(cursor.getString(2));
            String accountType = cursor.getString(3);
            String expiryDate = cursor.getString(4);
            int balance = Integer.parseInt(cursor.getString(5));
            userDataBase.add(new UserDataBase(name, accountNo, pin, accountType, balance, expiryDate));
        }
    }

    public UserDataBase getUserData(int i){
     UserDataBase userData = userDataBase.get(i);
     return userData;
    }

    public void updateUserData(int i, int balance){
        userDBWrite = userDataBaseHelper.getWritableDatabase();
        UserDataBase userData = userDataBase.get(i);
        cursor.moveToFirst();
        String Name = userDataBase.get(i).getName();
        while(cursor.moveToNext()){
            if(Name.equals(cursor.getString(1))){
                contentValues.put("pin", userData.getPin());
                contentValues.put("balance", balance);
                userDBWrite.update("userDetails",contentValues,"userName=?",new String[]{userData.getName()});
                System.out.println("\n\n\n\n\n\n\n" + cursor.getString(5)+"\n\n\n\n\n\n");
            }

        }
    }
}