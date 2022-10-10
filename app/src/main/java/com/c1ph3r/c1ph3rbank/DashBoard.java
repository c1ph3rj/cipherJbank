package com.c1ph3r.c1ph3rbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashBoard extends AppCompatActivity {
    UserDataBase userData = null;

    public DashBoard(UserDataBase userData){
        this.userData = userData;
    }
    public DashBoard(){
        Withdraw withdraw = new Withdraw();
        this.userData = withdraw.userData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Intent intent = getIntent();
        userData = (UserDataBase)intent.getSerializableExtra("value");

        BottomNavigationView bottomNavigation = findViewById(R.id.BottomNav);

        getSupportFragmentManager().beginTransaction().replace(R.id.SecondActivity, new DashboardLayout(userData)).commit();
        bottomNavigation.setSelectedItemId(R.id.dashboard_icon);
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch(item.getItemId()){
                    case R.id.dashboard_icon:
                        fragment = new DashboardLayout(userData);
                        break;
                    case R.id.Settings_icon:
                        fragment = new SettingsLayout();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.SecondActivity, fragment).commit();
                return true;
            }
        });
    }
}