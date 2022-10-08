package com.c1ph3r.c1ph3rbank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.c1ph3r.c1ph3rbank.controller.UserDetail;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    UserDetail userDetail;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDetail = new UserDetail(MainActivity.this);
        try {
            userDetail.getData();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClickLoginBtn(View view) {
        System.out.println(userDetail.userDataBase.get(0).getName());
    }

}