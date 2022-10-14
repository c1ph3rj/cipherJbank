package com.c1ph3r.c1ph3rbank.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// Method to create a Table and Access the Table.
public class TransactionHelper extends SQLiteOpenHelper {
    public TransactionHelper(@Nullable Context context, @Nullable String name) {
        super(context, name+"Transactions", null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE credit (id integer PRIMARY KEY AUTOINCREMENT, transactions text)");
        sqLiteDatabase.execSQL("CREATE TABLE debit (id integer PRIMARY KEY AUTOINCREMENT, transactions text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
