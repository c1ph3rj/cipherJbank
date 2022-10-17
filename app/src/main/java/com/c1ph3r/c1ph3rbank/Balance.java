package com.c1ph3r.c1ph3rbank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.c1ph3r.c1ph3rbank.Model.UserDataBase;
import com.google.android.material.button.MaterialButton;

public class Balance extends AppCompatActivity {
    TextView displayBalance;
    MaterialButton backBtnBalance;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        try{
            // Getting the index value in the  using the intent.
            Intent intent = getIntent();
            UserDataBase userData = (UserDataBase) intent.getSerializableExtra(getString(R.string.value_String));
            displayBalance = findViewById(R.id.displayBalance);
            backBtnBalance = findViewById(R.id.backBtnBalance);
            // Displaying the balance in the text view.
            displayBalance.setText("₹ " + userData.getBalance());
            System.out.println(userData.getBalance());
            backBtnBalance.setOnClickListener(view -> {
                Intent intent1 = new Intent(Balance.this, DashBoard.class);
                startActivity(intent1);
                finish();
            });
        }catch(Exception e){
            System.out.println(e +" | Balance ");
        }

    }
    public void onBackPressed(){
        // When back pressed.
        Intent intent = new Intent(Balance.this, DashBoard.class);
        startActivity(intent);
        finish();
    }
}