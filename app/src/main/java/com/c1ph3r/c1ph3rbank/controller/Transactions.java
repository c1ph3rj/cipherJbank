package com.c1ph3r.c1ph3rbank.controller;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.c1ph3r.c1ph3rbank.model.TransactionHelper;

import java.util.ArrayList;

public class Transactions {
    public ArrayList<String> getDebit(TransactionHelper transactionHelper){
        ArrayList<String> debit = new ArrayList<>();
        SQLiteDatabase getDebit = transactionHelper.getReadableDatabase();
//        Cursor cursor = getDebit.query("debit",new String[]{"id","transactions"},null,null,null,null, "id" +" DESC","1");
//        cursor.moveToFirst();
//        while(cursor.moveToNext()){
//            debit.add(cursor.getString(1));
//        }
        Cursor cursor = getDebit.rawQuery("SELECT * FROM debit",null);
        debit.add(String.valueOf(cursor.getCount()));
        cursor.close();
        return debit;
    }

    public ArrayList<String> getCredit(TransactionHelper transactionHelper){
        ArrayList<String> credit = new ArrayList<>();
        SQLiteDatabase getCredit = transactionHelper.getReadableDatabase();
        Cursor cursor = getCredit.query("credit",new String[]{"id","transactions"},null,null,null,null, "id" +" DESC","1");
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            credit.add(cursor.getString(1));
        }
        cursor.close();
        return credit;
    }
}
