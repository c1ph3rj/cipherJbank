package com.c1ph3r.c1ph3rbank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.c1ph3r.c1ph3rbank.model.UserDataBaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DashBoard extends AppCompatActivity {
    UserDataBase userData;
    UserDataBaseHelper userDataBaseHelper;
    public DashBoard(){
        Withdraw withdraw = new Withdraw();
        this.userData = withdraw.userData;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        try{
            userDataBaseHelper = new UserDataBaseHelper(this);
            userDataBaseHelper.getUserDataBase();
            SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("IndexValue", Context.MODE_PRIVATE);
            int value = sharedPreferences.getInt("value", 0);
            userData = userDataBaseHelper.getUserData(value);

            BottomNavigationView bottomNavigation = findViewById(R.id.BottomNav);

            getSupportFragmentManager().beginTransaction().replace(R.id.SecondActivity, new DashboardLayout(userData)).commit();
            bottomNavigation.setSelectedItemId(R.id.dashboard_icon);
            bottomNavigation.setOnItemSelectedListener(item -> {
                Fragment fragment = null;
                switch(item.getItemId()){
                    case R.id.dashboard_icon:
                        fragment = new DashboardLayout(userData);
                        break;
                    case R.id.Settings_icon:
                        fragment = new SettingsLayout(userData);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.SecondActivity, fragment).commit();
                return true;
            });
        }catch (Exception e){
            System.out.println(e + "| DashBoard");
        }

    }
    public void onBackPressed(){
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
        alertDialogBuilder.setTitle("Logout").setMessage("Do you Want to Logout?").setPositiveButton("Yes", (dialogInterface, i1) -> {
            userDataBaseHelper = new UserDataBaseHelper(this);
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("IndexValue", Context.MODE_PRIVATE);
            int value = sharedPreferences.getInt("value",0);
            userDataBaseHelper.getUserDataBase();
            userData = userDataBaseHelper.getUserData(value);
            userData.setLoggedIn(false);
            userDataBaseHelper.updateUserData(value, userData.getBalance() , userData.isLoggedIn() );
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);}).setNegativeButton("No", (dialogInterface, i1) -> {
        }).show();
    }
}