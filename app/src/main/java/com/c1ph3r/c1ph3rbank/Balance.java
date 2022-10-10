package com.c1ph3r.c1ph3rbank;

import androidx.appcompat.app.AppCompatActivity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
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
                intent.putExtra("value", userData);
                startActivity(intent);
            }
        });
    }
}