package com.c1ph3r.c1ph3rbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.c1ph3r.c1ph3rbank.Model.UserDataBase;
import com.c1ph3r.c1ph3rbank.DBHelper.UserDataBaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TransactionsDetails extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_details);
        try{
            // Bottom navigation for debit and credit options.
            BottomNavigationView bottomNavigation = findViewById(R.id.BottomNavTransactions);
            UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(this);
            userDataBaseHelper.getUserDataBase();
            // getting the index value of the user who is logged in.
            SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences(getString(R.string.IndexValue_Strings), Context.MODE_PRIVATE);
            int value = sharedPreferences.getInt(getString(R.string.value_String), 0);
            UserDataBase userData = userDataBaseHelper.getUserData(value);
            getSupportFragmentManager().beginTransaction().replace(R.id.transactionPage, new debit_history(userData)).commit();
            bottomNavigation.setSelectedItemId(R.id.debit_btn);

            bottomNavigation.setOnItemSelectedListener(item -> {
                Fragment fragment = null;
                switch(item.getItemId()){
                    case R.id.debit_btn:
                        fragment = new debit_history(userData);
                        break;
                    case R.id.credit_btn:
                        fragment = new credit_history(userData);
                        break;
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.transactionPage, fragment).commit();
                }
                return true;
            });
        }catch(Exception e){
            System.out.println(e + "| Transaction Details.");
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
        finish();
    }
}