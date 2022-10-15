package com.c1ph3r.c1ph3rbank.controller;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.c1ph3r.c1ph3rbank.model.TransactionHelper;

import java.util.ArrayList;

// Class to get the transaction from the table and converting to the arraylist.
public class Transactions {

    // Method to return the Debit table as an arrayList using SQLite query.
    public ArrayList<String> getDebit(TransactionHelper transactionHelper) {
        ArrayList<String> debit = new ArrayList<>();
        SQLiteDatabase getTransaction = transactionHelper.getReadableDatabase();
        Cursor debit1 = getTransaction.query("debit", new String[]{"transactions"}, null, null, null, null, null);
        debit1.moveToFirst();
        do {
            debit.add(debit1.getString(0));
        } while (debit1.moveToNext());
        debit1.close();
        return debit;
    }

    // Method to return the credit table from the Db as an array list.
    public ArrayList<String> getCredit(TransactionHelper transactionHelper) {
        ArrayList<String> credit = new ArrayList<>();
        SQLiteDatabase getCredit = transactionHelper.getReadableDatabase();
        Cursor cursor = getCredit.query("credit", new String[]{"transactions"}, null, null, null, null, null);
        cursor.moveToFirst();
        do {
            credit.add(cursor.getString(0));
        } while ((cursor.moveToNext()));
        cursor.close();
        return credit;
    }
}
