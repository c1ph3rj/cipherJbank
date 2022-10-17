package com.c1ph3r.c1ph3rbank.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import com.c1ph3r.c1ph3rbank.Model.UserDataBase;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

// Method to Create and access the User Data Base DB.
public class UserDataBaseHelper extends SQLiteOpenHelper {

    // THIS IS THE WHERE THE DATA IS STORED. AND THIS IS THE PART OF THE MAIN-ACTIVITY.class
    public ArrayList<UserDataBase> userDataBase = new ArrayList<>();
    ContentValues contentValues;
    SQLiteDatabase userDBWrite;
    SQLiteDatabase userDBRead;
    Cursor cursor;

    // PREDEFINED DATA BASES METHODS AND CONSTRUCTORS.
    public UserDataBaseHelper(@Nullable Context context) {
        super(context, "userDataBase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table userDetails(accountNumber text, userName text, pin text, accountType text, expiryDate text, balance text, loggedIn text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // Adding Data to the SQLite DB
    public boolean addUserToUserDetails(String userName, String pin, String accountNo, String accountType, String balance, String expiryDate) {
        SQLiteDatabase userDataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("accountNumber", accountNo);
        contentValues.put("userName", userName);
        contentValues.put("pin", pin);
        contentValues.put("accountType", accountType);
        contentValues.put("expiryDate", expiryDate);
        contentValues.put("balance", balance);
        contentValues.put("loggedIn", "false");
        long insert = userDataBase.insert("userDetails", null, contentValues);
        // returns false if the data does not add on the sqlite DB.
        return insert != -1;
    }

    // Getting the userDataBase SQLite DB and saving it into an Model class.
    public void getUserDataBase(){
        contentValues = new ContentValues();
        userDBRead = this.getReadableDatabase();
        // Tables inside the DB .
        String[] tableNames = {"accountNumber","userName", "pin", "accountType", "expiryDate", "balance", "loggedIn"};
        cursor = userDBRead.query("userDetails", tableNames,null,null,null,null,null);
        // Getting the values from the DB and assign it to the Model Class.
        if(cursor.moveToFirst()) {
            do {
                String accountNo = cursor.getString(0);
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
    public void updateUserData(int i, int balance,boolean loggedIn){
        userDBWrite = this.getWritableDatabase();
        String[] tableNames = {"accountNumber","userName", "pin", "accountType", "expiryDate", "balance", "loggedIn"};
        Cursor cursor = userDBWrite.query("userDetails", tableNames,null,null,null,null,null);
        getUserDataBase();
        UserDataBase userData = getUserData(i);
        cursor.moveToFirst();
        String Name = userData.getName();
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

    public int isUserPresentInDB(TextInputEditText userName, TextInputEditText pin){
        SQLiteDatabase userDB = this.getReadableDatabase();
        Cursor matchUserName = userDB.rawQuery("SELECT userName FROM userDetails WHERE userName = ?", new String[]{String.valueOf(userName.getText())});
        Cursor pinMatch = userDB.rawQuery("SELECT userName , pin FROM userDetails WHERE userName =? AND pin = ?", new String[]{String.valueOf(userName.getText()), String.valueOf(pin.getText())});
        matchUserName.moveToFirst();
        int value = -1;
        if (matchUserName.getCount() == 1 && matchUserName.getString(0).equals(String.valueOf(userName.getText()))) {
            pinMatch.moveToFirst();
            if (pinMatch.getCount() == 1 && ((matchUserName.getString(0).equals(String.valueOf(userName.getText()))) && pinMatch.getString(1).equals(String.valueOf(pin.getText())))) {
                // Verified using query.
                Cursor cursor = userDB.rawQuery("SELECT * FROM userDetails", null);
                cursor.moveToFirst();
                do {
                    if (cursor.getString(1).equals(String.valueOf(userName.getText())))
                        value = cursor.getPosition();
                } while (cursor.moveToNext());
                pinMatch.close();
                matchUserName.close();
                return value;
            }
            pinMatch.close();
            matchUserName.close();
            return -1;

        }
        pinMatch.close();
        matchUserName.close();
        return -2;
    }



}
