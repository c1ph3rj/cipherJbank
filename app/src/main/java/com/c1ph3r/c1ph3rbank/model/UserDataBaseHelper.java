package com.c1ph3r.c1ph3rbank.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.c1ph3r.c1ph3rbank.controller.UserRegistration;

public class UserDataBaseHelper extends SQLiteOpenHelper {

    public UserDataBaseHelper(@Nullable Context context) {
        super(context, "userDataBase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table userDetails(accountNumber text, userName text, pin text, accountType text, expiryDate text, balance text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addUserToUserDetails(UserRegistration userRegistration, Context context){
        UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(context);
        SQLiteDatabase userDataBase = userDataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("accountNumber", userRegistration.accountNo);
        contentValues.put("userName", userRegistration.userName);
        contentValues.put("pin", userRegistration.pin);
        contentValues.put("accountType", userRegistration.accountType);
        contentValues.put("expiryDate", userRegistration.expiryDate);
        contentValues.put("balance", userRegistration.balance);

        long insert = userDataBase.insert("userDetails",null,contentValues);
        return insert != -1;
    }

}
