package com.c1ph3r.c1ph3rbank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.google.android.material.button.MaterialButton;


public class DashboardLayout extends Fragment {
    UserDataBase userData;
    TextView greetTheUser, ATM_UserName, ATM_AccountNo, ATM_ExpiryDate;
    View view;
    public DashboardLayout(){

    }

    public DashboardLayout(UserDataBase userData) {
        this.userData = userData;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dashboard_layout, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onStart() {
        super.onStart();
        view = getView();
        greetTheUser = view.findViewById(R.id.greetTheUser);
        ATM_UserName = view.findViewById(R.id.ATM_UserName);
        ATM_AccountNo = view.findViewById(R.id.ATM_CardNo);
        ATM_ExpiryDate = view.findViewById(R.id.ATM_ExpiryDate);
        if(view != null){
            greetTheUser.setText(greetTheUser.getText()+ userData.getName());
            ATM_UserName.setText( userData.getName());
            ATM_AccountNo.setText(String.valueOf( userData.getAccountNo()));
            ATM_ExpiryDate.setText( userData.getExpiryDate());
        }

    }

    public void onClickSendMoneyBtn(View view) {
        Intent intent = new Intent(getActivity(), MainActivity.class);

        startActivity(intent);

    }

}