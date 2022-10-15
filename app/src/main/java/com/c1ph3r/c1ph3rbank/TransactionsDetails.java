package com.c1ph3r.c1ph3rbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceControl;
import android.view.View;

import com.c1ph3r.c1ph3rbank.controller.Transactions;
import com.c1ph3r.c1ph3rbank.controller.UserDetail;
import com.c1ph3r.c1ph3rbank.model.TransactionHelper;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.c1ph3r.c1ph3rbank.model.UserDataBaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class TransactionsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_details);
        BottomNavigationView bottomNavigation = findViewById(R.id.BottomNavTransactions);
        UserDataBaseHelper userDataBaseHelper = new UserDataBaseHelper(this);
        userDataBaseHelper.getUserDataBase();
        SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("IndexValue", Context.MODE_PRIVATE);
        int value = sharedPreferences.getInt("value", 0);
        UserDataBase userData = userDataBaseHelper.getUserData(value);
        getSupportFragmentManager().beginTransaction().replace(R.id.transactionPage, new debit_history(userData)).commit();
        bottomNavigation.setSelectedItemId(R.id.dashboard_icon);

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch(item.getItemId()){
                    case R.id.debit_btn:
                        fragment = new debit_history(userData);
                        break;
                    case R.id.credit_btn:
                        fragment = new credit_history(userData);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.transactionPage, fragment).commit();
                return true;
            }
        });
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
        finish();
    }

    public void onClickBackOnTransaction(View view) {
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
        finish();
    }
}