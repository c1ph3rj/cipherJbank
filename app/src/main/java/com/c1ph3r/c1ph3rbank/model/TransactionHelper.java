package com.c1ph3r.c1ph3rbank.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

// Method to create a Table and Access the Table.
public class TransactionHelper extends SQLiteOpenHelper {
    public TransactionHelper(@Nullable Context context, @Nullable String name) {
        super(context, name+"Transactions", null , 1);
    }

    // Method to Create Debit, and Credit tables. if they are not exists.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE credit (id integer PRIMARY KEY AUTOINCREMENT, transactions text)");
        sqLiteDatabase.execSQL("CREATE TABLE debit (id integer PRIMARY KEY AUTOINCREMENT, transactions text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // Method to return the credit table from the Db as an array list.
    public ArrayList<String> getCredit() {
        ArrayList<String> credit = new ArrayList<>();
        SQLiteDatabase getCredit = this.getReadableDatabase();
        Cursor cursor = getCredit.query("credit", new String[]{"transactions"}, null, null, null, null, null);
        cursor.moveToFirst();
        do {
            credit.add(cursor.getString(0));
        } while ((cursor.moveToNext()));
        cursor.close();
        return credit;
    }

    // Method to return the Debit table as an arrayList using SQLite query.
    public ArrayList<String> getDebit() {
        ArrayList<String> debit = new ArrayList<>();
        SQLiteDatabase getTransaction = this.getReadableDatabase();
        Cursor debit1 = getTransaction.query("debit", new String[]{"transactions"}, null, null, null, null, null);
        debit1.moveToFirst();
        do {
            debit.add(debit1.getString(0));
        } while (debit1.moveToNext());
        debit1.close();
        return debit;
    }
}
