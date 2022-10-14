package com.c1ph3r.c1ph3rbank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.google.android.material.button.MaterialButton;

public class Balance extends AppCompatActivity {
    TextView displayBalance;
    MaterialButton backBtnBalance;
    private UserDataBase userData;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        try{
            Intent intent = getIntent();
            userData = (UserDataBase) intent.getSerializableExtra("value");
            displayBalance = findViewById(R.id.displayBalance);
            backBtnBalance = findViewById(R.id.backBtnBalance);
            displayBalance.setText("₹ " + String.valueOf(userData.getBalance()));
            System.out.println(userData.getBalance());
            backBtnBalance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Balance.this, DashBoard.class);
                    startActivity(intent);
                    finish();
                }
            });
        }catch(Exception e){
            System.out.println(e.toString()+"");
        }

    }
    public void onBackPressed(){
        Intent intent = new Intent(Balance.this, DashBoard.class);
        startActivity(intent);
        finish();
    }
}