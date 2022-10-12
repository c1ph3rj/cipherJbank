package com.c1ph3r.c1ph3rbank.controller;

import com.c1ph3r.c1ph3rbank.MainActivity;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class UpdateUserDetails {

    private MainActivity mainActivity;
    public UpdateUserDetails(){

    }

    public UpdateUserDetails(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void putData(UserDataBase data1) throws IOException, JSONException {
        File output = new File("C:\\Users\\Jeeva Prakash\\AndroidStudioProjects\\cipherJbank\\app\\src\\main\\assets\\Database.json");
        InputStream input = mainActivity.getAssets().open("Database.json");
        output.setReadable(true);
        output.setWritable(true);
        int length = input.available();
        byte [] values = new byte[length];
        input.read(values);
        input.close();
        JSONArray data = new JSONArray(new String(values, "UTF-8"));
        UserDetail userDetail = new UserDetail();
        for(int i = 0;i<data.length();i++){
            if(data1.getName().equals(data.getJSONObject(i).get("name"))){
                JSONObject updateData = data.getJSONObject(i);
                updateData.put("balance", data1.getBalance());
                updateData.put("pin", data1.getPin());
            }
        }
        String updatedData = data.toString();
        values = updatedData.getBytes();
        FileWriter fileWriter = new FileWriter("C:\\Users\\Jeeva Prakash\\AndroidStudioProjects\\cipherJbank\\app\\src\\main\\assets\\Database.json");
        fileWriter.write(updatedData);
        System.out.println("DONE\nDONE\nDONE\nDONE\nDONE\nDONE\nDONE\nDONE\nDONE\nDONE\nDONE\nDONE\n");

        }
}
