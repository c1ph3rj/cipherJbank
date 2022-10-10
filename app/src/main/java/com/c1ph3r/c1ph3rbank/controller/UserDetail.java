package com.c1ph3r.c1ph3rbank.controller;

import com.c1ph3r.c1ph3rbank.MainActivity;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class UserDetail {

    public String name;


    private final MainActivity mainActivity;
    public JSONArray data;
    public ArrayList<UserDataBase> userDataBase = new ArrayList<>();

    public UserDetail(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public void getData() throws IOException, JSONException {
        InputStream input = mainActivity.getAssets().open("Database.json");
        int length = input.available();
        byte [] values = new byte[length];
        input.read(values);
        input.close();
        data = new JSONArray(new String(values, "UTF-8"));
        for (int i = 0;i<data.length();i++) {
            //Storing the object values in a variables.
            String name = (String)((JSONObject) data.get(i)).get("name");
            int accountNo = ((int)((JSONObject) data.get(i)).get("accountNo"));
            int pin = ((int)((JSONObject) data.get(i)).get("pin"));
            String accountType = (String)((JSONObject) data.get(i)).get("accountType");
            int balance = ((int)((JSONObject) data.get(i)).get("balance"));
            String expiryDate = (String)((JSONObject) data.get(i)).get("ExpiryDate");
            //Adding the Object values to the ArrayList of Class constructors.
            userDataBase.add(new UserDataBase(name, accountNo, pin, accountType, balance, expiryDate));
        }
    }
}