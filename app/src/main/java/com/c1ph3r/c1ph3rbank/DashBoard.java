package com.c1ph3r.c1ph3rbank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.c1ph3r.c1ph3rbank.Model.UserDataBase;
import com.c1ph3r.c1ph3rbank.DBHelper.UserDataBaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DashBoard extends AppCompatActivity {
    // Declaring the variables.
    UserDataBase userData;
    UserDataBaseHelper userDataBaseHelper;
    public DashBoard(){
        Withdraw withdraw = new Withdraw();
        this.userData = withdraw.userData;
    }

    // Things to be done on Dashboard created.
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        try{
            // Initializing the db.
            userDataBaseHelper = new UserDataBaseHelper(this);
            userDataBaseHelper.getUserDataBase();
            // Get the index value of the user.
            SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences(getString(R.string.IndexValue_Strings), Context.MODE_PRIVATE);
            int value = sharedPreferences.getInt(getString(R.string.value_String), 0);
            userData = userDataBaseHelper.getUserData(value);
            // Dashboard Bottom Navigation.
            BottomNavigationView bottomNavigation = findViewById(R.id.BottomNav);
            // Default start fragment.
            getSupportFragmentManager().beginTransaction().replace(R.id.SecondActivity, new DashboardLayout(userData)).commit();
            bottomNavigation.setSelectedItemId(R.id.dashboard_icon);
            // Actions to  be done on bottom navigation when it is clicked.
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
                if(fragment!=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.SecondActivity, fragment).commit();
                }
                return true;
            });
        }catch (Exception e){
            System.out.println(e + "| DashBoard");
        }

    }
    public void onBackPressed(){
        // When back press the Asking the user need to logout or not.
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
        alertDialogBuilder.setTitle(getString(R.string.Logout_text)).setMessage(getString(R.string.Do_you_want_to_logout)).setPositiveButton(getString(R.string.Yes_String), (dialogInterface, i1) -> {
            // Changing the state of the logged in if the user press yes.
            userDataBaseHelper = new UserDataBaseHelper(this);
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.IndexValue_Strings), Context.MODE_PRIVATE);
            int value = sharedPreferences.getInt(getString(R.string.value_String),0);
            userDataBaseHelper.getUserDataBase();
            userData = userDataBaseHelper.getUserData(value);
            userData.setLoggedIn(false);
            userDataBaseHelper.updateUserData(value, userData.getBalance() , userData.isLoggedIn() );
            // Back to the login screen.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);}).setNegativeButton(getString(R.string.No_String), (dialogInterface, i1) -> {
        }).show();
    }
}