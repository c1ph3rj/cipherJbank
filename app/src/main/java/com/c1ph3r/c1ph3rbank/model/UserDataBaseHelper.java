package com.c1ph3r.c1ph3rbank.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
}